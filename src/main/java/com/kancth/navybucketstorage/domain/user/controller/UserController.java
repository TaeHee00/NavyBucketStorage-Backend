package com.kancth.navybucketstorage.domain.user.controller;

import com.kancth.navybucketstorage.domain.user.dto.EmailCheckResponse;
import com.kancth.navybucketstorage.domain.user.dto.RegisterRequest;
import com.kancth.navybucketstorage.domain.user.dto.RegisterResponse;
import com.kancth.navybucketstorage.domain.user.service.UserService;
import com.kancth.navybucketstorage.global.exception.dto.Message;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(RegisterResponse.of(userService.register(request)));
    }

    @GetMapping("/check-email")
    public ResponseEntity<EmailCheckResponse> checkEmail(@RequestParam("email") @Email(message = "이메일 형식이 올바르지 않습니다.") String email) {
        return ResponseEntity.ok(userService.checkEmail(email));
    }
}
