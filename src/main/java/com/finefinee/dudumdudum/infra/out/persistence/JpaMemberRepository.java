package com.finefinee.dudumdudum.infra.out.persistence;

import com.finefinee.dudumdudum.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMemberRepository extends JpaRepository<Member, String> {

}
