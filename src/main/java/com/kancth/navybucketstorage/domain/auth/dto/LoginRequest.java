package com.kancth.navybucketstorage.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email이 입력되지 않았습니다.") @Email(message = "이메일 형식이 올바르지 않습니다.") String email,
        @NotBlank(message = "Password가 입력되지 않았씁니다.") String password
) {
}
