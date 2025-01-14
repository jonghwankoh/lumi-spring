package com.jonghwan.typing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
    private boolean success;
    private String message;

    public PostResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
