package com.example.newsfeed.user.dto.request;

import com.example.newsfeed.image.dto.request.ImageRequestDto;
import lombok.Getter;

@Getter
public class UserSaveRequestDto {

    private final String email;
    private final String name;
    private final String password;
    private final ImageRequestDto image;

    public UserSaveRequestDto(String email, String name, String password, ImageRequestDto image) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.image = image;
    }
}
