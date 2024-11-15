package com.kancth.navybucketstorage.domain.auth.service;

import com.kancth.navybucketstorage.domain.auth.dto.LoginRequest;
import com.kancth.navybucketstorage.domain.auth.dto.LoginResponse;
import com.kancth.navybucketstorage.domain.security.entity.JwtToken;
import com.kancth.navybucketstorage.domain.security.service.JwtService;
import com.kancth.navybucketstorage.domain.user.entity.User;
import com.kancth.navybucketstorage.domain.user.exception.InvalidPasswordException;
import com.kancth.navybucketstorage.domain.user.exception.UserNotFoundException;
import com.kancth.navybucketstorage.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        // 이메일 존재 확인
        User user = userRepository.findByEmail(request.email())
                                  .orElseThrow(UserNotFoundException::new);
        // 비밀번호 확인
        this.checkPassword(user, request.password());


        JwtToken jwtToken = jwtService.createJwtToken(user);
        return LoginResponse.of(jwtToken, user);
    }

    private void checkPassword(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }
    }

    public User getCurrentUser(HttpServletRequest request) {
        return (User) request.getAttribute("currentUser");
    }
}
