package com.jonghwan.typing.security.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private final AuthUserDTO authUserDTO;

    public CustomOAuth2User(AuthUserDTO authUserDTO) {
        this.authUserDTO = authUserDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return authUserDTO.getRole();
            }
        });

        return collection;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return authUserDTO.getName();
    }

    public String getUsername() {
        return authUserDTO.getUsername();
    }
}
