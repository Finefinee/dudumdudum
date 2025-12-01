package com.finefinee.dudumdudum.application.service;

import com.finefinee.dudumdudum.application.port.in.SignUpCommand;
import com.finefinee.dudumdudum.application.port.in.SignUpUseCase;
import com.finefinee.dudumdudum.application.port.out.MemberRepository;
import com.finefinee.dudumdudum.domain.member.Member;
import com.finefinee.dudumdudum.domain.member.MemberStatus;
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

        String encodedPassword = passwordEncoder.encode(command.getPassword());

        MemberStatus status = MemberStatus.ACTIVE;
        if (command.getRole() == Role.TEACHER) {
            status = MemberStatus.PENDING;
        }

        Member member = Member.builder()
                .id(null)
                .memberId(command.getMemberId())
                .name(command.getName())
                .password(encodedPassword)
                .grade(command.getGrade())
                .classNumber(command.getClassNumber())
                .role(command.getRole())
                .status(status)
                .build();

        memberRepository.save(member);
        
        return member.getId();
    }

}
