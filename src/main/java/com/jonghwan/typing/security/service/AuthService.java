package com.jonghwan.typing.security.service;

import com.jonghwan.typing.security.dto.CustomOAuth2User;
import com.jonghwan.typing.entity.Member;
import com.jonghwan.typing.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;

    public Member getCurrentAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof UserDetails userDetails)) {
            throw new IllegalStateException("Unauthorized access or invalid authentication.");
        }

        return memberRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found for the given token"));
    }
}
