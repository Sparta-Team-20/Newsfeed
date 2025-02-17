package com.example.newsfeed.user.dto.request;

import com.example.newsfeed.image.dto.request.UserImageRequestDto;
import com.example.newsfeed.image.entity.UserImage;
import lombok.Getter;

@Getter
public class UserSaveRequestDto {

    private final String email;
    private final String name;
    private final String password;
    private final UserImageRequestDto image;

    public UserSaveRequestDto(String email, String name, String password, UserImageRequestDto image) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.image = image;
    }
}
