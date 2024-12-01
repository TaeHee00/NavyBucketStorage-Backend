package com.kancth.navybucketstorage.domain.file.controller;

import com.kancth.navybucketstorage.domain.file.dto.CreateFileListResponse;
import com.kancth.navybucketstorage.domain.file.dto.CreateFileRequest;
import com.kancth.navybucketstorage.domain.file.service.FileService;
import com.kancth.navybucketstorage.global.interceptor.auth.Auth;
import com.kancth.navybucketstorage.global.interceptor.auth.AuthType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
@RestController
public class FileController {

    private final FileService fileService;

    @Auth(authType = AuthType.USER)
    @PostMapping
    public ResponseEntity<CreateFileListResponse> upload(@ModelAttribute CreateFileRequest createFileRequest, HttpServletRequest request) {
        // TODO: 같은 이름으로 된 파일 업로드 시 덮어쓰기 -> 객체 버전관리 기능 만들어서 실수 삭제 or 덮어쓰기 실수 방지
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(fileService.create(createFileRequest, request));
    }

    @GetMapping("/{bucketName}/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String bucketName, @PathVariable String fileName) {
        Resource resource = fileService.download(bucketName, fileName);

        String contentType;
        try {
            contentType = Files.probeContentType(resource.getFile().toPath());
        } catch (IOException e) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8) + "\"")
                .body(resource);
    }

    // TODO: 파일 삭제
    // file ID로 할 경우 History로 변경한다 하더라도 PK 값이 바뀔 가능성 농후 근데 상관없지 어차피 update하면 바꾸면되는ㄴ데
    @DeleteMapping("/{fileId}")
    @Auth(authType = AuthType.USER)
    public ResponseEntity<Void> delete(@PathVariable Long fileId, HttpServletRequest request) {
        fileService.delete(fileId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // bucket Private/Public 여부
    // PreSigned URL
}
