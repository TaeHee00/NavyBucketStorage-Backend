package com.kancth.navybucketstorage.domain.bucket.repository;

import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import com.kancth.navybucketstorage.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {
    List<Bucket> findAllByOwner(User owner);

    Optional<Bucket> findByNameAndOwner_Id(String name, Long id);
}
