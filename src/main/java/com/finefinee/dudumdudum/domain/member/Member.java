package com.finefinee.dudumdudum.domain.member;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
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

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public void setPasswordToEncoded(String password) {
        this.password = password;
    }

    public static Member create(String memberId, String name, String password, Integer grade, Integer classNumber, Role role) {
        MemberStatus initialStatus = (role == Role.TEACHER) ? MemberStatus.PENDING : MemberStatus.ACTIVE;
        return new Member(null, memberId, name, password, grade, classNumber, role, initialStatus);
    }

    public void approve() {
        this.status = MemberStatus.ACTIVE;
    }

}
