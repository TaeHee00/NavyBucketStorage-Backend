package com.kancth.navybucketstorage.domain.security.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class JwtToken {
    private String accessToken;
    private RefreshToken refreshToken;

    public static JwtToken of(String accessToken, RefreshToken refreshToken) {
        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
