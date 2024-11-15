package com.kancth.navybucketstorage.domain.bucket.repository;

import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {
}
