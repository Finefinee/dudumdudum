package com.finefinee.dudumdudum.domain.member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private Integer grade;

    private Integer classNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member(String name, String password, Integer classNumber, Integer grade, Role role) {
        this.name = name;
        this.password = password;
        this.classNumber = classNumber;
        this.grade = grade;
        this.role = role;
    }
}
