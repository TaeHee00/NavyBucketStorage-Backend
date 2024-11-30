package com.kancth.navybucketstorage.domain.file.exception;

import com.kancth.navybucketstorage.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends BaseException {
    public FileNotFoundException() {
        super("존재하지 않는 파일입니다.", HttpStatus.NOT_FOUND);
    }
}
