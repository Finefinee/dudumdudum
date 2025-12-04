package com.finefinee.dudumdudum.application.port.in;

import com.finefinee.dudumdudum.domain.member.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpCommand {
    private String memberId;
    private String name;
    private String password;
    private Integer grade;
    private Integer classNumber;
    private Role role;

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Integer getGrade() {
        return grade;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public Role getRole() {
        return role;
    }
}
