package com.kancth.navybucketstorage.domain.security.entity;

import lombok.*;

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
