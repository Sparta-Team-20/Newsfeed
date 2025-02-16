package com.example.newsfeed.user.dto.response;

import com.example.newsfeed.image.dto.response.UserImageResponse;
import com.example.newsfeed.user.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponse {

    private final Long id;
    private final String name;
    private final UserImageResponse image;

    private UserInfoResponse(Long id, String name, UserImageResponse image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public static UserInfoResponse of(User user, UserImageResponse image) {
        return new UserInfoResponse(user.getId(), user.getName(), image);
    }
}
