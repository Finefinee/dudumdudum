package com.finefinee.dudumdudum.application.port.out;

import com.finefinee.dudumdudum.domain.member.Member;

import java.util.Optional;

import java.util.UUID;

public interface MemberRepository {
    void save(Member member);
    Optional<Member> findById(UUID id);
    Optional<Member> findByMemberId(String memberId);
}
