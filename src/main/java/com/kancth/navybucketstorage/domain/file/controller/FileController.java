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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
                             .body(fileService.ownerUpload(createFileRequest, request));
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

    @DeleteMapping("/{fileId}")
    @Auth(authType = AuthType.USER)
    public ResponseEntity<Void> delete(@PathVariable Long fileId, HttpServletRequest request) {
        fileService.delete(fileId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // TODO: PreSigned URL
    @PostMapping("/{bucketName}")
    public ResponseEntity<CreateFileListResponse> preSignedUpload(
            @PathVariable String bucketName,
            @RequestParam("credential") String credential,
            @ModelAttribute MultipartFile[] files
    ) {
        // bucketName을 꼭 넣어야하는가?
        // 아니면 PreSigned URL을 따로 파고 body에 넣어야하나?
        // bucketName이랑 credential을 같이 넣어야하나? claims에 다 들어가있는데?
        // 그냥 검증용으로 둘다 넣기? <- 이게 괜찮은거같은데?
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(fileService.preSignedUpload(bucketName, credential, files));
    }
}
