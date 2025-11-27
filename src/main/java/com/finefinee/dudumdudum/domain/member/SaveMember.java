package com.finefinee.dudumdudum.domain.member;

import com.finefinee.dudumdudum.application.port.out.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SaveMember {

    private final MemberRepository memberRepository;

    public void save(Member member) {
        memberRepository.save(member);
    }
}

