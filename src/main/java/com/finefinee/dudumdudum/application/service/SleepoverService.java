package com.finefinee.dudumdudum.application.service;

import com.finefinee.dudumdudum.application.port.in.SleepoverUseCase;
import com.finefinee.dudumdudum.application.port.out.MemberRepository;
import com.finefinee.dudumdudum.application.port.out.SleepoverRepository;
import com.finefinee.dudumdudum.domain.exception.BusinessException;
import com.finefinee.dudumdudum.domain.exception.ErrorCode;
import com.finefinee.dudumdudum.domain.member.Member;
import com.finefinee.dudumdudum.domain.sleepover.Sleepover;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SleepoverService implements SleepoverUseCase {

    private final SleepoverRepository sleepoverRepository;
    private final MemberRepository memberRepository;

    @Override
    public void applySleepover(java.util.UUID memberId) {
        checkApplicationTime();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        
        Sleepover sleepover = Sleepover.create(member);
        sleepoverRepository.save(sleepover);
    }

    private void checkApplicationTime() {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek day = now.getDayOfWeek();
        int hour = now.getHour();

        boolean isSaturday = day == DayOfWeek.SATURDAY;
        boolean isSunday = day == DayOfWeek.SUNDAY && hour < 18;

        if (!isSaturday && !isSunday) {
            throw new BusinessException(ErrorCode.SLEEPOVER_DATE_INVALID);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sleepover> getAllSleepovers() {
        return sleepoverRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sleepover> getMySleepovers(java.util.UUID memberId) {
        return sleepoverRepository.findByMemberId(memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sleepover> getRecentSleepovers() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(DayOfWeek.SATURDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        
        // If today is Sunday, startOfWeek is yesterday (Saturday).
        // If today is Saturday, startOfWeek is today.
        // If today is Monday-Friday, we need to decide if we show "last weekend" or "next weekend".
        // Assuming "Recent" means the current active application window or the one just passed if it's Monday.
        // However, the requirement says "most recent week's Sat-Sun 6PM".
        // If I am on Monday, the most recent one is the past weekend.
        // If I am on Saturday, it is this weekend.
        
        // Let's find the *latest* Saturday that has passed or is today.
        if (now.getDayOfWeek() != DayOfWeek.SATURDAY && now.getDayOfWeek() != DayOfWeek.SUNDAY) {
             // If Mon-Fri, go back to previous Saturday
             startOfWeek = now.with(java.time.temporal.TemporalAdjusters.previous(DayOfWeek.SATURDAY)).withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else if (now.getDayOfWeek() == DayOfWeek.SUNDAY) {
             startOfWeek = now.minusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        
        LocalDateTime endOfWeek = startOfWeek.plusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0); // Sunday 18:00

        return sleepoverRepository.findByAppliedAtBetween(startOfWeek, endOfWeek);
    }
}
