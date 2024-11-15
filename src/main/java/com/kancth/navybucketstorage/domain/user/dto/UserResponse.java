package com.kancth.navybucketstorage.domain.user.dto;

import com.kancth.navybucketstorage.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record UserResponse(
        Long id,
        String username,
        String email
) {
    public static UserResponse of(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
