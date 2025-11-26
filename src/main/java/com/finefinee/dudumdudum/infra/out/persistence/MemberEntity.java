package com.finefinee.dudumdudum.infra.out.persistence;

import com.finefinee.dudumdudum.domain.member.Member;
import com.finefinee.dudumdudum.domain.member.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity {

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

    public MemberEntity(String name, String password, Integer classNumber, Integer grade, Role role) {
        this.name = name;
        this.password = password;
        this.classNumber = classNumber;
        this.grade = grade;
        this.role = role;
    }

    public static MemberEntity from(Member member) {
        return MemberEntity.builder()
                .id(member.getId())
                .name(member.getName())
                .password(member.getPassword())
                .grade(member.getGrade())
                .classNumber(member.getClassNumber())
                .role(member.getRole())
                .build();
    }

    public Member toDomain() {
        return Member.builder()
                .id(id)
                .name(name)
                .password(password)
                .grade(grade)
                .classNumber(classNumber)
                .role(role)
                .build();
    }
}
