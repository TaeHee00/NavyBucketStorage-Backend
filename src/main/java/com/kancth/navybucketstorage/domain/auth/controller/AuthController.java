package com.kancth.navybucketstorage.domain.auth.controller;

import com.kancth.navybucketstorage.domain.auth.dto.LoginRequest;
import com.kancth.navybucketstorage.domain.auth.dto.LoginResponse;
import com.kancth.navybucketstorage.domain.auth.service.AuthService;
import com.kancth.navybucketstorage.global.interceptor.auth.Auth;
import com.kancth.navybucketstorage.global.interceptor.auth.AuthType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @Auth(authType = AuthType.NONE)
    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // TODO: 회원가입 했으면 또 뭐하냐?
    // TODO: 이미지 업로드
}
