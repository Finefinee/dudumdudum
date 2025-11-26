package com.finefinee.dudumdudum.infra.out.persistence;

import com.finefinee.dudumdudum.application.port.out.MemberRepository;
import com.finefinee.dudumdudum.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public void save(Member member) {
        // 1. 도메인(Member) -> 엔티티(MemberEntity) 변환
        // (변환 로직은 Entity 내부에 static 메서드나 Mapper 클래스로 만듦)
        MemberEntity entity = MemberEntity.from(member);

        // 2. JPA 저장
        jpaMemberRepository.save(entity);
    }

    @Override
    public Optional<Member> findById(String id) {
        // 1. JPA 조회 (결과는 Entity)
        Optional<MemberEntity> entityOptional = jpaMemberRepository.findById(id);

        // 2. 엔티티(MemberEntity) -> 도메인(Member) 변환해서 리턴
        return entityOptional.map(MemberEntity::toDomain);
    }
}