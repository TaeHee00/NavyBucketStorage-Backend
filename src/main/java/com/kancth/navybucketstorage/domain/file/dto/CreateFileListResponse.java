package com.kancth.navybucketstorage.domain.file.dto;

import com.kancth.navybucketstorage.domain.bucket.dto.BucketResponse;
import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import com.kancth.navybucketstorage.domain.file.entity.File;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record CreateFileListResponse(
        BucketResponse bucket,
        List<FileResponse> successedFileList,
        List<String> failedFileList
) {
    public static CreateFileListResponse of(Bucket bucket, List<File> successedFileList, List<String> failedFileList) {
        return CreateFileListResponse.builder()
                .bucket(BucketResponse.of(bucket))
                .successedFileList(successedFileList.stream().map(FileResponse::of).toList())
                .failedFileList(failedFileList)
                .build();
    }
}
