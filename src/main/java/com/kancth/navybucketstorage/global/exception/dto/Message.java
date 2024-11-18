package com.kancth.navybucketstorage.global.exception.dto;

import lombok.*;

@Builder
public record Message(
        String message
) {
    public static Message of(String message) {
        return Message.builder()
                .message(message)
                .build();
    }
}
