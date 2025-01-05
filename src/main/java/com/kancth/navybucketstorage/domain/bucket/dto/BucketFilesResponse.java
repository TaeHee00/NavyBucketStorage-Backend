package com.kancth.navybucketstorage.domain.bucket.dto;

import com.kancth.navybucketstorage.domain.bucket.BucketAccessLevel;
import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import com.kancth.navybucketstorage.domain.file.dto.FileResponse;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record BucketFilesResponse (
        Long id,
        String bucketName,
        BucketAccessLevel accessLevel,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<FileResponse> files
){
    public static BucketFilesResponse of(Bucket bucket, List<FileResponse> files) {
        return BucketFilesResponse.builder()
                .id(bucket.getId())
                .bucketName(bucket.getName())
                .accessLevel(bucket.getAccessLevel())
                .description(bucket.getDescription())
                .createdAt(bucket.getCreatedAt())
                .updatedAt(bucket.getUpdatedAt())
                .files(files)
                .build();
    }
}
