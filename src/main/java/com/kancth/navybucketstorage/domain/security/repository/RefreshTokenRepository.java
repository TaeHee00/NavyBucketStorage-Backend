package com.kancth.navybucketstorage.domain.security.repository;

import com.kancth.navybucketstorage.domain.security.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByAccessToken(String accessToken);
    void deleteByAccessToken(String accessToken);
}
