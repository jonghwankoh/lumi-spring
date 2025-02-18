package com.jonghwan.typing.shared.base.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteResponse {
    private boolean success;
    private String message;

    public DeleteResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
