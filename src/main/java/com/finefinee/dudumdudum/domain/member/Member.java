package com.finefinee.dudumdudum.domain.member;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private String id;

    private String name;

    private String password;

    private Integer grade;

    private Integer classNumber;

    private Role role;

    public Member(String name, String password, Integer classNumber, Integer grade, Role role) {
        this.name = name;
        this.password = password;
        this.classNumber = classNumber;
        this.grade = grade;
        this.role = role;
    }
}
