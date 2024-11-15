package com.kancth.navybucketstorage.domain.bucket.controller;

import com.kancth.navybucketstorage.domain.bucket.dto.BucketResponse;
import com.kancth.navybucketstorage.domain.bucket.dto.CreateBucketRequest;
import com.kancth.navybucketstorage.domain.bucket.service.BucketService;
import com.kancth.navybucketstorage.global.interceptor.auth.Auth;
import com.kancth.navybucketstorage.global.interceptor.auth.AuthType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/buckets")
@RestController
public class BucketController {

    private final BucketService bucketService;

    @PostMapping
    @Auth(authType = AuthType.USER)
    public ResponseEntity<BucketResponse> create(@RequestBody CreateBucketRequest createBucketRequest, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(BucketResponse.of(bucketService.create(createBucketRequest, request)));
    }

    // TODO: 회원가입 했으면 또 뭐하냐?
    // TODO: 이미지 업로드
}
