package com.jonghwan.typing.shared.security.member;

import lombok.Builder;

@Builder
public record LoginMember(
        Long id,
        String name,
        String email,
        String role
) {
}