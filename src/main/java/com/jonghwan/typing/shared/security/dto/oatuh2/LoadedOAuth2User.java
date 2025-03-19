package com.jonghwan.typing.shared.security.dto.oatuh2;

import com.jonghwan.typing.shared.security.member.Member;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Builder
@RequiredArgsConstructor
public class LoadedOAuth2User implements OAuth2User {
    private final Member member;

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of("userKey", member.getUserKey(), "name", member.getName(), "role", member.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(member.getRole()));
    }

    @Override
    public String getName() {
        return member.getUserKey();
    }

    public Long getMemberId() {
        return member.getId();
    }
}
