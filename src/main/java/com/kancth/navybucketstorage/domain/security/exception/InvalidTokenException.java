package com.kancth.navybucketstorage.domain.security.exception;

import com.kancth.navybucketstorage.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException() {
        super("유효하지않은 Token입니다.", HttpStatus.UNAUTHORIZED);
    }
}
