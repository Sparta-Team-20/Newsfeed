package com.example.newsfeed.user.dto.request;

import lombok.Getter;

@Getter
public class UserSaveRequestDto {

    private final String email;
    private final String name;
    private final String password;
    private final String image;

    public UserSaveRequestDto(String email, String name, String password, String image) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.image = image;
    }
}
