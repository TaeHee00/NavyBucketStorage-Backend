package com.kancth.navybucketstorage.domain.security.exception;

import com.kancth.navybucketstorage.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AccessTokenNotFoundException extends BaseException {
    public AccessTokenNotFoundException() {
        super("유효하지 않은 AccessToken입니다.", HttpStatus.UNAUTHORIZED);
    }
}
