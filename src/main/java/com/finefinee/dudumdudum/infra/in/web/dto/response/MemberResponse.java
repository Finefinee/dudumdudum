package com.finefinee.dudumdudum.infra.in.web.dto.response;

import com.finefinee.dudumdudum.domain.member.Member;
import com.finefinee.dudumdudum.domain.member.MemberStatus;
import com.finefinee.dudumdudum.domain.member.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MemberResponse {
    private UUID id;
    private String memberId;
    private String name;
    private Integer grade;
    private Integer classNumber;
    private Role role;
    private MemberStatus status;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .name(member.getName())
                .grade(member.getGrade())
                .classNumber(member.getClassNumber())
                .role(member.getRole())
                .status(member.getStatus())
                .build();
    }
}
