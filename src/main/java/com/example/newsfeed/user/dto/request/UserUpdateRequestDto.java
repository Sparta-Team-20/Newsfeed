package com.example.newsfeed.user.dto.request;

import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    private final String password;
    private final String name;

    public UserUpdateRequestDto(String password, String name) {
        this.password = password;
        this.name = name;
    }
}
