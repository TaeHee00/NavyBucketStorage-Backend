package com.kancth.navybucketstorage.domain.security.exception;

import com.kancth.navybucketstorage.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends BaseException {
    public ExpiredTokenException() {
        super("만료된 Token입니다.", HttpStatus.UNAUTHORIZED);
    }
}
