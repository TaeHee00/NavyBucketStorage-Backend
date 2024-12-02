package com.kancth.navybucketstorage.domain.bucket.dto;

import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record CreateUploadPreSignedUrlResponse(
        String url,
        BucketResponse bucket,
        LocalDateTime createdAt,
        LocalDateTime expiredAt
) {
    public static CreateUploadPreSignedUrlResponse of(String url, Bucket bucket, long expiredSecond) {
        LocalDateTime now = LocalDateTime.now();

        return CreateUploadPreSignedUrlResponse.builder()
                .url(url)
                .bucket(BucketResponse.of(bucket))
                .createdAt(now)
                .expiredAt(now.plusSeconds(expiredSecond))
                .build();
    }
}
