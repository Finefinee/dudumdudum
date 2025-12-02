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

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplySleepoverService implements ApplySleepoverUseCase {

    private static final int APPLICATION_DEADLINE_HOUR = 18;

    private final SleepoverRepository sleepoverRepository;
    private final MemberRepository memberRepository;
    private final Clock clock;

    @Override
    public void applySleepover(UUID memberId) {
        checkApplicationTime();
        checkDuplicateApplication(memberId);

        Member member = memberRepository.findByIdOrThrow(memberId);
        
        Sleepover sleepover = Sleepover.create(member);
        sleepoverRepository.save(sleepover);
    }

    private void checkApplicationTime() {
        LocalDateTime now = LocalDateTime.now(clock);
        DayOfWeek day = now.getDayOfWeek();
        int hour = now.getHour();

        // 토요일 전체 또는 일요일 오후 6시 이전만 허용
        boolean isValidTime = (day == DayOfWeek.SATURDAY) || 
                              (day == DayOfWeek.SUNDAY && hour < APPLICATION_DEADLINE_HOUR);

        if (!isValidTime) {
            throw new BusinessException(ErrorCode.SLEEPOVER_DATE_INVALID);
        }
    }

    private void checkDuplicateApplication(UUID memberId) {
        LocalDateTime now = LocalDateTime.now(clock);
        
        // 이번 주의 시작(월요일 00:00)과 끝(일요일 23:59:59) 계산
        LocalDateTime weekStart = now.with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
        LocalDateTime weekEnd = now.with(DayOfWeek.SUNDAY).toLocalDate().atTime(23, 59, 59);
        
        // 이번 주에 이미 신청한 내역이 있는지 확인
        List<Sleepover> existingApplications = sleepoverRepository
            .findByMemberIdAndAppliedAtBetween(memberId, weekStart, weekEnd);
        
        if (!existingApplications.isEmpty()) {
            throw new BusinessException(ErrorCode.DUPLICATE_SLEEPOVER_APPLICATION);
        }
    }
}
