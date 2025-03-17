package com.jonghwan.typing.shared.base.dto;

public record Response(
        String message
) {
    public static Response of(final String message) {
        return new Response(message);
    }
}
