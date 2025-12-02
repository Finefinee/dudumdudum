package com.finefinee.dudumdudum.application.port.out;

import com.finefinee.dudumdudum.domain.exception.BusinessException;
import com.finefinee.dudumdudum.domain.exception.ErrorCode;
import com.finefinee.dudumdudum.domain.member.Member;
import com.finefinee.dudumdudum.domain.member.MemberStatus;
import com.finefinee.dudumdudum.domain.member.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
    void save(Member member);
    Optional<Member> findById(UUID id);
    Optional<Member> findByMemberId(String memberId);
    List<Member> findAll();
    List<Member> findByRoleAndStatus(Role role, MemberStatus status);
    
    default Member findByIdOrThrow(UUID id) {
        return findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }
    
    default Member findByMemberIdOrThrow(String memberId) {
        return findByMemberId(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
