package com.kancth.navybucketstorage.domain.user.controller;

import com.kancth.navybucketstorage.domain.user.dto.RegisterRequest;
import com.kancth.navybucketstorage.domain.user.dto.RegisterResponse;
import com.kancth.navybucketstorage.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegisterResponse> register(RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(RegisterResponse.of(userService.register(request)));
    }
}
