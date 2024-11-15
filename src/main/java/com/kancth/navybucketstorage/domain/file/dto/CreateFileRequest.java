package com.kancth.navybucketstorage.domain.file.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record CreateFileRequest(
        @NotBlank(message = "파일을 저장할 Bucket이 선택되지 않았습니다.") Long bucketId,
        @NotBlank(message = "파일이 업로드되지 않았습니다.") MultipartFile[] files
) {
}
