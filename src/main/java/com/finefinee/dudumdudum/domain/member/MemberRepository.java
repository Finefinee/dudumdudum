package com.finefinee.dudumdudum.domain.member;

import java.util.Optional;

public interface MemberRepository {
    void save(Member member);
    Optional<Member> findById(String id);
}
