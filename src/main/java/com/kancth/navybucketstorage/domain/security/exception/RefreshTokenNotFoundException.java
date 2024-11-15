package com.kancth.navybucketstorage.domain.security.exception;

import com.kancth.navybucketstorage.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class RefreshTokenNotFoundException extends BaseException {
    public RefreshTokenNotFoundException() {
        super("유효하지 않은 RefreshToken입니다.", HttpStatus.UNAUTHORIZED);
    }
}
