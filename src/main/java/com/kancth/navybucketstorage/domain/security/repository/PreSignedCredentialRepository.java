package com.kancth.navybucketstorage.domain.security.repository;

import com.kancth.navybucketstorage.domain.security.entity.PreSignedCredential;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreSignedCredentialRepository extends CrudRepository<PreSignedCredential, String> {
    Optional<PreSignedCredential> findByCredential(String credential);
}
