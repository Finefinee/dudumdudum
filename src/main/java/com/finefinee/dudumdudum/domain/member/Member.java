package com.finefinee.dudumdudum.domain.member;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String memberId; // Login ID (String)

    private String name;

    private String password;

    private Integer grade;

    private Integer classNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member(String memberId, String name, String password, Integer classNumber, Integer grade, Role role) {
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.classNumber = classNumber;
        this.grade = grade;
        this.role = role;
    }

    public void setPasswordToEncoded(String password) {
        this.password = password;
    }
}
