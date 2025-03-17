package com.jonghwan.typing.shared.security.handler;

import com.jonghwan.typing.shared.base.dto.Response;
import com.jonghwan.typing.shared.base.exception.BadRequestException;
import com.jonghwan.typing.shared.base.exception.ForbiddenException;
import com.jonghwan.typing.shared.base.exception.NotFoundException;
import com.jonghwan.typing.shared.base.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleForbiddenException(ForbiddenException ex) {
        log.info("Forbidden: {}", ex.getMessage());
        return Response.of(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleBadRequestException(BadRequestException ex) {
        log.info("Bad request: {}", ex.getMessage());
        return Response.of(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleNotFoundException(NotFoundException ex) {
        log.info("Resource not found: {}", ex.getMessage());
        return Response.of(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response handleUnauthorizedException(UnauthorizedException ex) {
        log.info("Invalid token exception occurred: {}", ex.getMessage());
        return Response.of(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleGenericException(Exception ex) {
        log.error("Unhandled exception occurred", ex);
        return Response.of(ex.getMessage());
    }
}
