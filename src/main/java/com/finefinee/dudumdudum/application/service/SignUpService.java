package com.finefinee.dudumdudum.application.service;

import com.finefinee.dudumdudum.application.port.in.SignUpCommand;
import com.finefinee.dudumdudum.application.port.in.SignUpUseCase;
import com.finefinee.dudumdudum.application.port.out.MemberRepository;
import com.finefinee.dudumdudum.domain.exception.BusinessException;
import com.finefinee.dudumdudum.domain.exception.ErrorCode;
import com.finefinee.dudumdudum.domain.member.Member;
import com.finefinee.dudumdudum.domain.member.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService implements SignUpUseCase {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public java.util.UUID joinMember(SignUpCommand command) {

        if (command.getRole() == Role.ADMIN) {
            throw new BusinessException(ErrorCode.ADMIN_SIGNUP_NOT_ALLOWED);
        }

        String encodedPassword = passwordEncoder.encode(command.getPassword());

        Member member = Member.create(
                command.getMemberId(),
                command.getName(),
                encodedPassword,
                command.getGrade(),
                command.getClassNumber(),
                command.getRole()
        );

        memberRepository.save(member);
        
        return member.getId();
    }

}
