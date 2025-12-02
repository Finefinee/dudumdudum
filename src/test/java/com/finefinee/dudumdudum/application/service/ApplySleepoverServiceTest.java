package com.finefinee.dudumdudum.application.service;

import com.finefinee.dudumdudum.application.port.out.MemberRepository;
import com.finefinee.dudumdudum.application.port.out.SleepoverRepository;
import com.finefinee.dudumdudum.domain.exception.BusinessException;
import com.finefinee.dudumdudum.domain.exception.ErrorCode;
import com.finefinee.dudumdudum.domain.member.Member;
import com.finefinee.dudumdudum.domain.member.MemberStatus;
import com.finefinee.dudumdudum.domain.member.Role;
import com.finefinee.dudumdudum.domain.sleepover.Sleepover;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplySleepoverServiceTest {

    @InjectMocks
    private ApplySleepoverService applySleepoverService;

    @Mock
    private SleepoverRepository sleepoverRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private Clock clock;

    @Test
    @DisplayName("평일에 외박 신청을 하면 예외가 발생한다")
    void applySleepoverOnWeekdayThrowsException() {
        // given
        UUID memberId = UUID.randomUUID();
        
        // 월요일 오전 10시로 고정
        LocalDateTime monday = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10)
                .withMinute(0);
        Clock fixedClock = Clock.fixed(monday.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());

        // when & then
        assertThatThrownBy(() -> applySleepoverService.applySleepover(memberId))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.SLEEPOVER_DATE_INVALID);
    }

    @Test
    @DisplayName("토요일에는 외박 신청이 가능하다")
    void applySleepoverOnSaturdaySucceeds() {
        // given
        UUID memberId = UUID.randomUUID();
        Member member = new Member(memberId, "student1", "Student", "password", 1, 1, Role.STUDENT, MemberStatus.ACTIVE);
        
        // 토요일 오후 3시로 고정
        LocalDateTime saturday = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
                .withHour(15)
                .withMinute(0);
        Clock fixedClock = Clock.fixed(saturday.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
        when(memberRepository.findByIdOrThrow(memberId)).thenReturn(member);
        when(sleepoverRepository.findByMemberIdAndAppliedAtBetween(eq(memberId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        // when & then - 예외가 발생하지 않음
        applySleepoverService.applySleepover(memberId);
    }

    @Test
    @DisplayName("일요일 오후 6시 이전에는 외박 신청이 가능하다")
    void applySleepoverOnSundayBeforeDeadlineSucceeds() {
        // given
        UUID memberId = UUID.randomUUID();
        Member member = new Member(memberId, "student1", "Student", "password", 1, 1, Role.STUDENT, MemberStatus.ACTIVE);
        
        // 일요일 오후 5시로 고정
        LocalDateTime sunday = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
                .withHour(17)
                .withMinute(0);
        Clock fixedClock = Clock.fixed(sunday.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
        when(memberRepository.findByIdOrThrow(memberId)).thenReturn(member);
        when(sleepoverRepository.findByMemberIdAndAppliedAtBetween(eq(memberId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        // when & then - 예외가 발생하지 않음
        applySleepoverService.applySleepover(memberId);
    }

    @Test
    @DisplayName("일요일 오후 6시 이후에는 외박 신청이 불가능하다")
    void applySleepoverOnSundayAfterDeadlineThrowsException() {
        // given
        UUID memberId = UUID.randomUUID();
        
        // 일요일 오후 6시로 고정
        LocalDateTime sunday = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
                .withHour(18)
                .withMinute(0);
        Clock fixedClock = Clock.fixed(sunday.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());

        // when & then
        assertThatThrownBy(() -> applySleepoverService.applySleepover(memberId))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.SLEEPOVER_DATE_INVALID);
    }

    @Test
    @DisplayName("같은 주에 이미 외박 신청을 했다면 중복 신청이 불가능하다")
    void duplicateSleepoverApplicationInSameWeekThrowsException() {
        // given
        UUID memberId = UUID.randomUUID();
        Member member = new Member(memberId, "student1", "Student", "password", 1, 1, Role.STUDENT, MemberStatus.ACTIVE);
        
        // 토요일 오후 3시로 고정
        LocalDateTime saturday = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
                .withHour(15)
                .withMinute(0);
        Clock fixedClock = Clock.fixed(saturday.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
        
        // 이미 이번 주에 신청한 외박 내역이 있음
        Sleepover existingSleepover = Sleepover.create(member);
        when(sleepoverRepository.findByMemberIdAndAppliedAtBetween(eq(memberId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(existingSleepover));

        // when & then
        assertThatThrownBy(() -> applySleepoverService.applySleepover(memberId))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.DUPLICATE_SLEEPOVER_APPLICATION);
    }

    @Test
    @DisplayName("이번 주에 신청 내역이 없으면 외박 신청이 가능하다")
    void applySleepoverWithNoExistingApplicationSucceeds() {
        // given
        UUID memberId = UUID.randomUUID();
        Member member = new Member(memberId, "student1", "Student", "password", 1, 1, Role.STUDENT, MemberStatus.ACTIVE);
        
        // 토요일 오후 3시로 고정
        LocalDateTime saturday = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
                .withHour(15)
                .withMinute(0);
        Clock fixedClock = Clock.fixed(saturday.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
        when(memberRepository.findByIdOrThrow(memberId)).thenReturn(member);
        
        // 이번 주에 신청한 외박 내역이 없음
        when(sleepoverRepository.findByMemberIdAndAppliedAtBetween(eq(memberId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        // when & then - 예외가 발생하지 않음
        applySleepoverService.applySleepover(memberId);
    }
}
