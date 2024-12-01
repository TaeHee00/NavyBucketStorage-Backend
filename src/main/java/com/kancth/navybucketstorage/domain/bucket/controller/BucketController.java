package com.kancth.navybucketstorage.domain.bucket.controller;

import com.kancth.navybucketstorage.domain.bucket.dto.BucketResponse;
import com.kancth.navybucketstorage.domain.bucket.dto.CreateBucketResponse;
import com.kancth.navybucketstorage.domain.bucket.dto.CreateBucketRequest;
import com.kancth.navybucketstorage.domain.bucket.service.BucketService;
import com.kancth.navybucketstorage.global.interceptor.auth.Auth;
import com.kancth.navybucketstorage.global.interceptor.auth.AuthType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/buckets")
@RestController
public class BucketController {

    private final BucketService bucketService;

    @PostMapping
    @Auth(authType = AuthType.USER)
    public ResponseEntity<CreateBucketResponse> create(@RequestBody CreateBucketRequest createBucketRequest, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(CreateBucketResponse.of(bucketService.create(createBucketRequest, request)));
    }

    @GetMapping
    @Auth(authType = AuthType.USER)
    public ResponseEntity<List<BucketResponse>> list(HttpServletRequest request) {
        return ResponseEntity.ok(bucketService.list(request).stream().map(BucketResponse::of).toList());
    }

    @DeleteMapping("/{bucketId}")
    @Auth(authType = AuthType.USER)
    public ResponseEntity<Void> delete(@PathVariable Long bucketId, HttpServletRequest request) {
        bucketService.delete(bucketId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
