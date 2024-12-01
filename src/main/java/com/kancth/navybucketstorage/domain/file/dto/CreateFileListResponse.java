package com.kancth.navybucketstorage.domain.file.dto;

import com.kancth.navybucketstorage.domain.bucket.dto.CreateBucketResponse;
import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import com.kancth.navybucketstorage.domain.file.entity.File;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record CreateFileListResponse(
        CreateBucketResponse bucket,
        List<FileResponse> successedFileList,
        List<String> failedFileList
) {
    public static CreateFileListResponse of(Bucket bucket, List<File> successedFileList, List<String> failedFileList) {
        return CreateFileListResponse.builder()
                .bucket(CreateBucketResponse.of(bucket))
                .successedFileList(successedFileList.stream().map(FileResponse::of).toList())
                .failedFileList(failedFileList)
                .build();
    }
}
