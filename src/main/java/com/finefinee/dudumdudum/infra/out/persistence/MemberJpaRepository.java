package com.finefinee.dudumdudum.infra.out.persistence;

import com.finefinee.dudumdudum.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByMemberId(String memberId);
}
