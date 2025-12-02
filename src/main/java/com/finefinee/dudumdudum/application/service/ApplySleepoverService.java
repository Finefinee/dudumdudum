package com.finefinee.dudumdudum.application.service;

import com.finefinee.dudumdudum.application.port.in.ApplySleepoverUseCase;
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
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplySleepoverService implements ApplySleepoverUseCase {

    private static final int APPLICATION_DEADLINE_HOUR = 18;

    private final SleepoverRepository sleepoverRepository;
    private final MemberRepository memberRepository;

    @Override
    public void applySleepover(UUID memberId) {
        checkApplicationTime();

        Member member = memberRepository.findByIdOrThrow(memberId);
        
        Sleepover sleepover = Sleepover.create(member);
        sleepoverRepository.save(sleepover);
    }

    private void checkApplicationTime() {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek day = now.getDayOfWeek();
        int hour = now.getHour();

        boolean isSaturday = day == DayOfWeek.SATURDAY;
        boolean isSunday = day == DayOfWeek.SUNDAY && hour < APPLICATION_DEADLINE_HOUR;

        if (!isSaturday && !isSunday) {
            throw new BusinessException(ErrorCode.SLEEPOVER_DATE_INVALID);
        }
    }
}
