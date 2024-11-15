package com.kancth.navybucketstorage.domain.auth.dto;

import com.kancth.navybucketstorage.domain.security.entity.JwtToken;
import com.kancth.navybucketstorage.domain.user.entity.User;
import com.kancth.navybucketstorage.global.interceptor.auth.AuthType;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record LoginResponse(
        String accessToken,
        String refreshToken,
        String username,
        AuthType authType
) {
    public static LoginResponse of(JwtToken jwtToken, User user) {
        return LoginResponse.builder()
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken().getRefreshToken())
                .username(user.getUsername())
                .authType(user.getAuthType())
                .build();
    }
}
