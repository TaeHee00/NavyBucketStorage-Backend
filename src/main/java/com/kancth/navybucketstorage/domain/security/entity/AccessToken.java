package com.kancth.navybucketstorage.domain.security.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class AccessToken {
    private String accessToken;

    public static AccessToken of(String accessToken) {
        return AccessToken.builder()
                .accessToken(accessToken)
                .build();
    }
}
