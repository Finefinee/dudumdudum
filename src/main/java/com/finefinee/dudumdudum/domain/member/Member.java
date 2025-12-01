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

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public Member(String memberId, String name, String password, Integer classNumber, Integer grade, Role role, MemberStatus status) {
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.classNumber = classNumber;
        this.grade = grade;
        this.role = role;
        this.status = status;
    }

    public void setPasswordToEncoded(String password) {
        this.password = password;
    }

    public static Member create(String memberId, String name, String password, Integer grade, Integer classNumber, Role role) {
        MemberStatus initialStatus = (role == Role.TEACHER) ? MemberStatus.PENDING : MemberStatus.ACTIVE;
        return new Member(memberId, name, password, classNumber, grade, role, initialStatus);
    }

    public void approve() {
        this.status = MemberStatus.ACTIVE;
    }

}
