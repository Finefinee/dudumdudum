package com.finefinee.dudumdudum.infra.out.persistence;

import com.finefinee.dudumdudum.application.port.out.MemberRepository;
import com.finefinee.dudumdudum.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public void save(Member member) {
        memberJpaRepository.save(member);
    }

    @Override
    public Optional<Member> findById(java.util.UUID id) {
        return memberJpaRepository.findById(id);
    }

    @Override
    public Optional<Member> findByMemberId(String memberId) {
        return memberJpaRepository.findByMemberId(memberId);
    }
}