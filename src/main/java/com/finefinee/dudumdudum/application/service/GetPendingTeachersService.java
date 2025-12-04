package com.finefinee.dudumdudum.application.service;

import com.finefinee.dudumdudum.application.port.in.GetPendingTeachersUseCase;
import com.finefinee.dudumdudum.application.port.out.MemberRepository;
import com.finefinee.dudumdudum.domain.member.Member;
import com.finefinee.dudumdudum.domain.member.MemberStatus;
import com.finefinee.dudumdudum.domain.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetPendingTeachersService implements GetPendingTeachersUseCase {

    private final MemberRepository memberRepository;

    @Override
    public List<Member> getPendingTeachers() {
        return memberRepository.findAll().stream()
                .filter(m -> m.getRole() == Role.TEACHER && m.getStatus() == MemberStatus.PENDING)
                .collect(Collectors.toList());
    }
}
