package com.finefinee.dudumdudum.infra.config.security;

import com.finefinee.dudumdudum.application.port.out.MemberRepository;
import com.finefinee.dudumdudum.domain.member.Member;
import com.finefinee.dudumdudum.domain.member.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with memberId: " + username));
        
        if (member.getStatus() == MemberStatus.PENDING) {
            throw new DisabledException("Account is pending approval");
        }
        
        return new CustomUserDetails(member);
    }
}
