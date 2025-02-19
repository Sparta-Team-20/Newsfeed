package com.example.newsfeed.auth.controller;

import com.example.newsfeed.auth.dto.request.LoginRequestDto;
import com.example.newsfeed.auth.dto.response.LoginResponseDto;
import com.example.newsfeed.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auths/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        LoginResponseDto token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(token);
    }
}
