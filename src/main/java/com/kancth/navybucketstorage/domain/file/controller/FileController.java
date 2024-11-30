package com.kancth.navybucketstorage.domain.file.controller;

import com.kancth.navybucketstorage.domain.file.dto.CreateFileListResponse;
import com.kancth.navybucketstorage.domain.file.dto.CreateFileRequest;
import com.kancth.navybucketstorage.domain.file.service.FileService;
import com.kancth.navybucketstorage.global.interceptor.auth.Auth;
import com.kancth.navybucketstorage.global.interceptor.auth.AuthType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
@RestController
public class FileController {

    private final FileService fileService;

    @Auth(authType = AuthType.USER)
    @PostMapping
    public ResponseEntity<CreateFileListResponse> upload(@ModelAttribute CreateFileRequest createFileRequest, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(fileService.create(createFileRequest, request));
    }
}
