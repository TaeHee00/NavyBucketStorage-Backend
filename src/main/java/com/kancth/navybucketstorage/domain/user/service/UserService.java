package com.kancth.navybucketstorage.domain.user.service;

import com.kancth.navybucketstorage.domain.user.dto.RegisterRequest;
import com.kancth.navybucketstorage.domain.user.entity.User;
import com.kancth.navybucketstorage.domain.user.exception.UserAlreadyExistsException;
import com.kancth.navybucketstorage.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User register(RegisterRequest request) {
        checkEmailDuplicate(request.email());

        return userRepository.save(User.create(request));
    }

    private void checkEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException();
        }
    }
}
