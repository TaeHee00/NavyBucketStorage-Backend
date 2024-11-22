package com.kancth.navybucketstorage.domain.user.service;

import com.kancth.navybucketstorage.domain.user.dto.EmailCheckResponse;
import com.kancth.navybucketstorage.domain.user.dto.RegisterRequest;
import com.kancth.navybucketstorage.domain.user.entity.User;
import com.kancth.navybucketstorage.domain.user.exception.UserAlreadyExistsException;
import com.kancth.navybucketstorage.domain.user.exception.UserNotFoundException;
import com.kancth.navybucketstorage.domain.user.repository.UserRepository;
import com.kancth.navybucketstorage.global.exception.dto.Message;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User register(RegisterRequest request) {
        checkEmailDuplicate(request.email());

        User user = User.create(request);
        user.setPassword(passwordEncoder.encode(request.password()));

        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    private void checkEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException();
        }
    }

    public EmailCheckResponse checkEmail(@Email(message = "이메일 형식이 올바르지 않습니다.") String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException();
        }
        return EmailCheckResponse.of(email, "생성 가능한 이메일 입니다.");
    }
}
