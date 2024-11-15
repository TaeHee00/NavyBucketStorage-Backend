package com.kancth.navybucketstorage.domain.user.exception;

import com.kancth.navybucketstorage.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends BaseException {
    public InvalidPasswordException() {
        super("비밀번호가 올바르지 않습니다. 다시 확인해 주세요.", HttpStatus.UNAUTHORIZED);
    }
}
