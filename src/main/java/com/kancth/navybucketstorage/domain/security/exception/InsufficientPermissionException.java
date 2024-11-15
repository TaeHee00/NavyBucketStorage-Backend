package com.kancth.navybucketstorage.domain.security.exception;

import com.kancth.navybucketstorage.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InsufficientPermissionException extends BaseException {
    public InsufficientPermissionException() {
        super("해당 작업을 수행할 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }
}
