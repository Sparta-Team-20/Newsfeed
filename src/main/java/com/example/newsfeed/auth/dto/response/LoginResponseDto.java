package com.example.newsfeed.auth.dto.response;


import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String token;
    private String expiredTime;

    private LoginResponseDto(String token, String expiredTime) {
        this.token = token;
        this.expiredTime = expiredTime;
    }

    public static LoginResponseDto of(String token, String expiredTime) {
        return new LoginResponseDto(token, expiredTime);
    }
}
