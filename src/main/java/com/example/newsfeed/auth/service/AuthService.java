package com.example.newsfeed.auth.service;

import com.example.newsfeed.auth.dto.response.LoginResponseDto;
import com.example.newsfeed.common.config.JwtUtil;
import com.example.newsfeed.common.config.PasswordEncoder;
import com.example.newsfeed.common.exception.CustomExceptionHandler;
import com.example.newsfeed.common.exception.ErrorCode;
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

    public LoginResponseDto login(String email, String password) {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_MATCH_EMAIL));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomExceptionHandler(ErrorCode.NOT_MATCH_PASSWORD);
        }

        String[] token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getName());
        return LoginResponseDto.of(token[0], token[1]);
    }

}
