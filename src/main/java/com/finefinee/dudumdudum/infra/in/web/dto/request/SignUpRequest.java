package com.finefinee.dudumdudum.infra.in.web.dto.request;

import com.finefinee.dudumdudum.application.port.in.SignUpCommand;
import com.finefinee.dudumdudum.domain.member.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpRequest {

    @NotBlank(message = "아이디는 필수입니다.")
    private String memberId;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    private Integer grade;

    private Integer classNumber;

    @NotNull(message = "역할은 필수입니다.")
    private Role role;

    public SignUpCommand toCommand() {
        return SignUpCommand.builder()
                .memberId(memberId)
                .name(name)
                .password(password)
                .grade(grade)
                .classNumber(classNumber)
                .role(role)
                .build();
    }


}
