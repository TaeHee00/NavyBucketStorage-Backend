package com.kancth.navybucketstorage.domain.bucket.exception;

import com.kancth.navybucketstorage.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class BucketNotFoundException extends BaseException {
    public BucketNotFoundException() {
        super("존재하지 않는 Bucket입니다.", HttpStatus.NOT_FOUND);
    }
}
