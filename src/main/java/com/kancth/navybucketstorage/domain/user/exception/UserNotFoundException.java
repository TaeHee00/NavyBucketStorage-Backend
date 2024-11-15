package com.kancth.navybucketstorage.domain.user.exception;

import com.kancth.navybucketstorage.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND);
    }
}
