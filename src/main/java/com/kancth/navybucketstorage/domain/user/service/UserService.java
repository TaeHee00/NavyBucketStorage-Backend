package com.kancth.navybucketstorage.domain.user.service;

import com.kancth.navybucketstorage.domain.user.dto.RegisterRequest;
import com.kancth.navybucketstorage.domain.user.entity.User;
import com.kancth.navybucketstorage.domain.user.exception.UserAlreadyExistsException;
import com.kancth.navybucketstorage.domain.user.repository.UserRepository;
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

    private void checkEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException();
        }
    }
}
