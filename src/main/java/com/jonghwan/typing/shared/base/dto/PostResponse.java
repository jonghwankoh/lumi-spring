package com.jonghwan.typing.shared.base.dto;

public record PostResponse(
        String message,
        Long id
) {
    public static PostResponse of(final String message, final Long id) {
        return new PostResponse(message, id);
    }
}

