package com.kancth.navybucketstorage.domain.bucket.dto;

import com.kancth.navybucketstorage.domain.bucket.BucketAccessLevel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateBucketRequest(
        @NotBlank(message = "bucket 이름 입력되지 않았습니다.") String name,
        @NotBlank(message = "Bucket Access Level을 선택하지 않았습니다.") BucketAccessLevel accessLevel,
        @NotBlank(message = "description이 입력되지 않았습니다.") String description
) {
}
