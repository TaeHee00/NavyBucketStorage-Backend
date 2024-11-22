package com.kancth.navybucketstorage.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "username이 입력되지 않았습니다.") String username,
        @NotBlank(message = "email이 입력되지 않았습니다.") @Email(message = "이메일 형식이 올바르지 않습니다.") String email,
        @NotBlank(message = "password가 입력되지 않았습니다.") String password
) {
}
