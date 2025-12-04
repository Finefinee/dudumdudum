package com.finefinee.dudumdudum.application.service;

import com.finefinee.dudumdudum.application.port.in.SignUpCommand;
import com.finefinee.dudumdudum.application.port.out.MemberRepository;
import com.finefinee.dudumdudum.domain.exception.BusinessException;
import com.finefinee.dudumdudum.domain.exception.ErrorCode;
import com.finefinee.dudumdudum.domain.member.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {

    @InjectMocks
    private SignUpService signUpService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("ADMIN 권한으로 회원가입 시 예외가 발생한다")
    void signUpWithAdminRoleThrowsException() {
        // given
        SignUpCommand command = SignUpCommand.builder()
                .memberId("admin")
                .password("password")
                .name("Admin")
                .role(Role.ADMIN)
                .build();

        // when & then
        assertThatThrownBy(() -> signUpService.joinMember(command))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.ADMIN_SIGNUP_NOT_ALLOWED);
    }
}
