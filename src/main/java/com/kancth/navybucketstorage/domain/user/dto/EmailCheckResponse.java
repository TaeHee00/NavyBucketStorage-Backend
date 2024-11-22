package com.kancth.navybucketstorage.domain.user.dto;

import lombok.Builder;

@Builder
public record EmailCheckResponse(
        String email,
        String message
) {
    public static EmailCheckResponse of(String email, String message) {
        return EmailCheckResponse.builder()
                .email(email)
                .message(message)
                .build();
    }
}
