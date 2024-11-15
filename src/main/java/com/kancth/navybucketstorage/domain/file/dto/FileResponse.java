package com.kancth.navybucketstorage.domain.file.dto;

import com.kancth.navybucketstorage.domain.file.entity.File;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FileResponse(
        Long id,
        String fileName,
        String url,
        long size,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static FileResponse of(File file) {
        return FileResponse.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .url(file.getUrl())
                .size(file.getSize())
                .createdAt(file.getCreatedAt())
                .updatedAt(file.getUpdatedAt())
                .build();
    }
}
