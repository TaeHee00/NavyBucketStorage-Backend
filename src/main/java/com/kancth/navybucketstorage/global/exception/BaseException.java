package com.kancth.navybucketstorage.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends RuntimeException {
    private final String message;
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public BaseException(String errorMessage, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = errorMessage;
    }
}
