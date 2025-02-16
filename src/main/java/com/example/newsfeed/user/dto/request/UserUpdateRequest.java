package com.example.newsfeed.user.dto.request;

import com.example.newsfeed.image.entity.UserImage;
import lombok.Getter;

import java.util.List;

@Getter
public class UserUpdateRequest {

    private final String password;
    private final String name;
    private final List<UserImage> images;

    public UserUpdateRequest(String password, String name, List<UserImage> images) {
        this.password = password;
        this.name = name;
        this.images = images;
    }
}
