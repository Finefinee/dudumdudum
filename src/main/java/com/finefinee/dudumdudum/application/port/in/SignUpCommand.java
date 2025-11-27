package com.finefinee.dudumdudum.application.port.in;

import com.finefinee.dudumdudum.domain.member.Member;
import com.finefinee.dudumdudum.domain.member.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpCommand {
    private String name;
    private String password;
    private Integer grade;
    private Integer classNumber;
    private Role role;

    public static Member toMember(SignUpCommand command) {
        return Member.builder()
                .name(command.getName())
                .password(command.getPassword())
                .grade(command.getGrade())
                .classNumber(command.getClassNumber())
                .role(command.getRole())
                .build();
    }
}
