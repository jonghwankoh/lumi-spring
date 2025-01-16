package com.jonghwan.typing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
    private boolean success;
    private String message;
    private Long resourceId;

    public PostResponse(boolean success, String message, Long resourceId) {
        this.success = success;
        this.resourceId = resourceId;
        this.message = message;
    }
}
