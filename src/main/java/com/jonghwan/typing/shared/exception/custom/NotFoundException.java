package com.jonghwan.typing.shared.exception.custom;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}