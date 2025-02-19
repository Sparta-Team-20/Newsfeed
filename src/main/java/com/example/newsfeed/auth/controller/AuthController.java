package com.example.newsfeed.auth.controller;

import com.example.newsfeed.auth.dto.request.LoginRequestDto;
import com.example.newsfeed.auth.dto.response.LoginResponseDto;
import com.example.newsfeed.auth.service.AuthService;
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

    // 로그인 (JWT 반환)
    @PostMapping("/auths/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    // 로그아웃 (JWT는 클라이언트에서 삭제)
    @PostMapping("/auths/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
