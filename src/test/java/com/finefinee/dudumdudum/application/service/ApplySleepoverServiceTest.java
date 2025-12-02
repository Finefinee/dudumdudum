package com.finefinee.dudumdudum.application.service;

import com.finefinee.dudumdudum.application.port.out.MemberRepository;
import com.finefinee.dudumdudum.application.port.out.SleepoverRepository;
import com.finefinee.dudumdudum.domain.exception.BusinessException;
import com.finefinee.dudumdudum.domain.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ApplySleepoverServiceTest {

    @InjectMocks
    private ApplySleepoverService applySleepoverService;

    @Mock
    private SleepoverRepository sleepoverRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("평일에 외박 신청을 하면 예외가 발생한다")
    void applySleepoverOnWeekdayThrowsException() {
        // given
        UUID memberId = UUID.randomUUID();

        // when & then (현재가 평일이라고 가정)
        assertThatThrownBy(() -> applySleepoverService.applySleepover(memberId))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.SLEEPOVER_DATE_INVALID);
    }
}
