package com.finefinee.dudumdudum.domain.member;

import com.finefinee.dudumdudum.infra.out.persistence.MemberEntity;

import java.util.Optional;

public interface MemberRepository {
    void save(MemberEntity member);
    Optional<MemberEntity> findById(String id);
}
