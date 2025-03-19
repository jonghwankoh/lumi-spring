package com.jonghwan.typing.shared.security.dto;

import lombok.Builder;

@Builder
public record AuthUserDTO(
        String username,
        String name,
        String role
) {
}
