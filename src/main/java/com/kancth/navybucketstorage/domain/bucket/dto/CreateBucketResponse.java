package com.kancth.navybucketstorage.domain.bucket.dto;

import com.kancth.navybucketstorage.domain.bucket.BucketAccessLevel;
import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import com.kancth.navybucketstorage.domain.user.dto.UserResponse;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record CreateBucketResponse(
        Long id,
        UserResponse owner,
        String bucketName,
        BucketAccessLevel accessLevel,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CreateBucketResponse of(Bucket bucket) {
        return CreateBucketResponse.builder()
                .id(bucket.getId())
                .owner(UserResponse.of(bucket.getOwner()))
                .bucketName(bucket.getName())
                .accessLevel(bucket.getAccessLevel())
                .description(bucket.getDescription())
                .createdAt(bucket.getCreatedAt())
                .updatedAt(bucket.getUpdatedAt())
                .build();
    }
}
