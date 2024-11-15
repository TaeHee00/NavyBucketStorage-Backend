package com.kancth.navybucketstorage.domain.file.controller;

import com.kancth.navybucketstorage.domain.file.dto.CreateFileListResponse;
import com.kancth.navybucketstorage.domain.file.dto.CreateFileRequest;
import com.kancth.navybucketstorage.domain.file.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
@RestController
public class FileController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<CreateFileListResponse> create(@RequestBody CreateFileRequest createFileRequest, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(fileService.create(createFileRequest, request));
    }
}
