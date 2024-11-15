package com.kancth.navybucketstorage.domain.security.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@RedisHash(value = "refreshToken", timeToLive = 86400000) // 1일
public class RefreshToken {

    @Id
    private String accessToken;
    private String refreshToken;

    public static RefreshToken of(String refreshToken, String accessToken) {
        return RefreshToken.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
    }
}
