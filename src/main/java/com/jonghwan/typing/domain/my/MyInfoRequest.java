package com.jonghwan.typing.domain.my;

import lombok.Builder;

@Builder
public record MyInfoRequest(
        Long memberId,
        String name,
        String email,
        String role
) {
}