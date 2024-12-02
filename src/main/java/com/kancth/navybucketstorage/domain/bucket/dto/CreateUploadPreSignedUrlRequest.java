package com.kancth.navybucketstorage.domain.bucket.dto;

import com.kancth.navybucketstorage.domain.bucket.BucketAccessLevel;
import com.kancth.navybucketstorage.domain.bucket.TimeType;
import jakarta.validation.constraints.NotBlank;

public record CreateUploadPreSignedUrlRequest(
        @NotBlank(message = "bucketID가 입력되지 않았습니다.") Long bucketId,
        @NotBlank(message = "Time Type이 정의되지 않았습니다.") TimeType timeType,
        @NotBlank(message = "Time을 설정하지 않았습니다.") Long time
) {
}
