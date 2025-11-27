package com.finefinee.dudumdudum.application.port.out;

import com.finefinee.dudumdudum.domain.member.Member;

import java.util.Optional;

public interface MemberRepository {
    void save(Member member);
    Optional<Member> findById(String id);
}
