package com.kancth.navybucketstorage.global.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedAccessException extends BaseException {
    public UnauthorizedAccessException() {
        super("접근 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
}
