package com.kancth.navybucketstorage.domain.user.exception;

import com.kancth.navybucketstorage.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException() {
        super("이미 등록된 이메일 입니다.", HttpStatus.CONFLICT);
    }
}
