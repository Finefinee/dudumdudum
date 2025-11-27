package com.finefinee.dudumdudum.application.service;

import com.finefinee.dudumdudum.application.port.in.SignUpCommand;
import com.finefinee.dudumdudum.application.port.in.SignUpUseCase;
import com.finefinee.dudumdudum.domain.member.Member;
import com.finefinee.dudumdudum.domain.member.SaveMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService implements SignUpUseCase {

    private final SaveMember saveMember;

    @Override
    public void JoinMember(SignUpCommand command) {

        Member member = SignUpCommand.toMember(command);
        saveMember.save(member);

    }
}
