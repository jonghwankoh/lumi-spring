package com.jonghwan.typing.domain.my;

import lombok.Builder;

@Builder
public record MyDTO(
        Long userId,
        String email,
        String name,
        String role
) {
}