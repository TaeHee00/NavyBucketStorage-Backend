package com.kancth.navybucketstorage.domain.bucket.service;

import com.kancth.navybucketstorage.domain.auth.service.AuthService;
import com.kancth.navybucketstorage.domain.bucket.TimeType;
import com.kancth.navybucketstorage.domain.bucket.dto.CreateBucketRequest;
import com.kancth.navybucketstorage.domain.bucket.dto.CreateUploadPreSignedUrlRequest;
import com.kancth.navybucketstorage.domain.bucket.dto.CreateUploadPreSignedUrlResponse;
import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import com.kancth.navybucketstorage.domain.bucket.exception.BucketNotFoundException;
import com.kancth.navybucketstorage.domain.bucket.repository.BucketRepository;
import com.kancth.navybucketstorage.domain.file.entity.File;
import com.kancth.navybucketstorage.domain.file.repository.FileRepository;
import com.kancth.navybucketstorage.domain.security.service.PreSignedCredentialService;
import com.kancth.navybucketstorage.domain.user.entity.User;
import com.kancth.navybucketstorage.global.exception.UnauthorizedAccessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BucketService {

    private final AuthService authService;
    private final BucketRepository bucketRepository;
    private final FileRepository fileRepository;
    private final PreSignedCredentialService preSignedCredentialService;

    public Bucket create(CreateBucketRequest createBucketRequest, HttpServletRequest request) {
        User user = authService.getCurrentUser(request);

        return bucketRepository.save(Bucket.create(createBucketRequest, user));
    }

    public List<Bucket> list(HttpServletRequest request) {
        User user = authService.getCurrentUser(request);

        return bucketRepository.findAllByOwner(user);
    }

    @Transactional
    public void delete(Long bucketId, HttpServletRequest request) {
        User user = authService.getCurrentUser(request);

        Bucket bucket = getBucket(bucketId);
        this.checkBucketOwner(bucket, user);

        bucketRepository.delete(bucket);

    }

    @Transactional
    public CreateUploadPreSignedUrlResponse createUploadPreSignedUrl(CreateUploadPreSignedUrlRequest preSignedRequest, HttpServletRequest request) {
        User user = authService.getCurrentUser(request);
        Bucket bucket = this.getBucket(preSignedRequest.bucketId());

        this.checkBucketOwner(bucket, user);

        // Time Type에 따라 Second로 치환
        Long expiredSecond = 0L;
        switch (preSignedRequest.timeType()) {
            case TimeType.HOUR -> expiredSecond = preSignedRequest.time() * 60 * 60;
            case MINUTE -> expiredSecond = preSignedRequest.time() * 60;
            case SECOND -> expiredSecond = preSignedRequest.time();
        }
        // Credential 생성
        String credential = preSignedCredentialService.genUploadPreSignedCredential(bucket.getOwner(), bucket, expiredSecond);
        String url = bucket.getName() + "." + bucket.getOwner().getId() + "?credential=" + credential;
        return CreateUploadPreSignedUrlResponse.of(url, bucket, expiredSecond);
    }

    public void checkBucketOwner(Bucket bucket, User user) {
        if (!bucket.getOwner().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException();
        }
    }

    public Bucket getBucket(Long bucketId) {
        return bucketRepository.findById(bucketId).orElseThrow(BucketNotFoundException::new);
    }

    public List<File> getBucketFiles(Long bucketId, HttpServletRequest request) {
        User user = authService.getCurrentUser(request);
        Bucket bucket = getBucket(bucketId);

        this.checkBucketOwner(bucket, user);

        return fileRepository.findAllByBucket(bucket);
    }

}
