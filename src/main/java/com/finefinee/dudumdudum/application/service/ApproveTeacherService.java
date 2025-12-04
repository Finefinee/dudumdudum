package com.finefinee.dudumdudum.application.service;

import com.finefinee.dudumdudum.application.port.in.ApproveTeacherUseCase;
import com.finefinee.dudumdudum.application.port.out.MemberRepository;
import com.finefinee.dudumdudum.domain.exception.BusinessException;
import com.finefinee.dudumdudum.domain.exception.ErrorCode;
import com.finefinee.dudumdudum.domain.member.Member;
import com.finefinee.dudumdudum.domain.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ApproveTeacherService implements ApproveTeacherUseCase {

    private final MemberRepository memberRepository;

    @Override
    public void approveTeacher(UUID memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        
        if (member.getRole() != Role.TEACHER) {
            throw new IllegalArgumentException("Not a teacher");
        }
        
        member.approve();
        memberRepository.save(member);
    }
}
