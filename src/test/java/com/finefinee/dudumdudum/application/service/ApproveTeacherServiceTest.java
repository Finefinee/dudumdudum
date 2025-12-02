package com.finefinee.dudumdudum.application.service;

import com.finefinee.dudumdudum.application.port.out.MemberRepository;
import com.finefinee.dudumdudum.domain.exception.BusinessException;
import com.finefinee.dudumdudum.domain.exception.ErrorCode;
import com.finefinee.dudumdudum.domain.member.Member;
import com.finefinee.dudumdudum.domain.member.MemberStatus;
import com.finefinee.dudumdudum.domain.member.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApproveTeacherServiceTest {

    @InjectMocks
    private ApproveTeacherService approveTeacherService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("선생님을 승인하면 상태가 ACTIVE로 변경된다")
    void approveTeacher() {
        // given
        UUID memberId = UUID.randomUUID();
        Member teacher = new Member(memberId, "teacher1", "Teacher", "password", null, null, Role.TEACHER, MemberStatus.PENDING);
        
        when(memberRepository.findByIdOrThrow(memberId)).thenReturn(teacher);

        // when
        approveTeacherService.approveTeacher(memberId);

        // then
        verify(memberRepository, times(1)).save(teacher);
    }

    @Test
    @DisplayName("회원을 찾을 수 없으면 예외가 발생한다")
    void memberNotFoundThrowsException() {
        // given
        UUID memberId = UUID.randomUUID();
        when(memberRepository.findByIdOrThrow(memberId)).thenThrow(new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        // when & then
        assertThatThrownBy(() -> approveTeacherService.approveTeacher(memberId))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_NOT_FOUND);
    }

    @Test
    @DisplayName("선생님이 아닌 회원을 승인하려 하면 예외가 발생한다")
    void approveNonTeacherThrowsException() {
        // given
        UUID memberId = UUID.randomUUID();
        Member student = new Member(memberId, "student1", "Student", "password", 1, 1, Role.STUDENT, MemberStatus.ACTIVE);
        
        when(memberRepository.findByIdOrThrow(memberId)).thenReturn(student);

        // when & then
        assertThatThrownBy(() -> approveTeacherService.approveTeacher(memberId))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.INVALID_ROLE);
    }
}
