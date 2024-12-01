package com.kancth.navybucketstorage.domain.file.repository;

import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import com.kancth.navybucketstorage.domain.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    boolean existsByBucketAndFileName(Bucket bucket, String fileName);

    Optional<File> findByUrl(String url);

    List<File> findAllByBucket(Bucket bucket);
}
