package com.jonghwan.typing.service;

import com.jonghwan.typing.dto.CustomOAuth2User;
import com.jonghwan.typing.entity.Member;
import com.jonghwan.typing.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;

    public Member getCurrentAuthenticatedUser() {
        return getCurrentOAuth2User();
    }

    private Member getCurrentOAuth2User() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof CustomOAuth2User oAuth2User)) {
            throw new IllegalStateException("Unauthorized access or invalid authentication.");
        }

        return memberRepository.findByUsername(oAuth2User.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found for the given token"));
    }
}
