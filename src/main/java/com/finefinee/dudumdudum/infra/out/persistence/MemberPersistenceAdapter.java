package com.finefinee.dudumdudum.infra.out.persistence;

import com.finefinee.dudumdudum.domain.member.MemberRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberPersistenceAdapter implements MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;

    public MemberPersistenceAdapter(JpaMemberRepository jpaMemberRepository) {
        this.jpaMemberRepository = jpaMemberRepository;
    }

    @Override
    public void save(MemberEntity member) {
        jpaMemberRepository.save(member);
    }

    @Override
    public Optional<MemberEntity> findById(String id) {
        return jpaMemberRepository.findById(id);
    }
}
