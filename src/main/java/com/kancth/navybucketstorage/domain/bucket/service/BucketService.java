package com.kancth.navybucketstorage.domain.bucket.service;

import com.kancth.navybucketstorage.domain.auth.service.AuthService;
import com.kancth.navybucketstorage.domain.bucket.dto.CreateBucketRequest;
import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import com.kancth.navybucketstorage.domain.bucket.exception.BucketNotFoundException;
import com.kancth.navybucketstorage.domain.bucket.repository.BucketRepository;
import com.kancth.navybucketstorage.domain.user.entity.User;
import com.kancth.navybucketstorage.global.exception.UnauthorizedAccessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BucketService {

    private final AuthService authService;
    private final BucketRepository bucketRepository;

    public Bucket create(CreateBucketRequest createBucketRequest, HttpServletRequest request) {
        User user = authService.getCurrentUser(request);

        return bucketRepository.save(Bucket.create(createBucketRequest, user));
    }

    public List<Bucket> list(HttpServletRequest request) {
        User user = authService.getCurrentUser(request);

        return bucketRepository.findAllByOwner(user);
    }
    public void checkBucketOwner(Bucket bucket, User user) {
        if (!bucket.getOwner().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException();
        }
    }

    public Bucket getBucket(Long bucketId) {
        return bucketRepository.findById(bucketId).orElseThrow(BucketNotFoundException::new);
    }

}
