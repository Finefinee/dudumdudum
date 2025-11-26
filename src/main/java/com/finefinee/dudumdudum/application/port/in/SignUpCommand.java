package com.finefinee.dudumdudum.application.port.in;

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


}
