package com.example.newsfeed.auth.service;

import com.example.newsfeed.common.config.JwtUtil;
import com.example.newsfeed.common.config.PasswordEncoder;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public String login(String email, String password) {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return jwtUtil.generateToken(user.getId(), user.getEmail(), user.getName());
    }

    public void logout() {
    }
}
