package com.example.newsfeed.user.dto.response;

import com.example.newsfeed.user.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

    private final Long id;
    private final String name;
    private final String image;

    private UserInfoResponseDto(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public static UserInfoResponseDto of(User user, String image) {
        return new UserInfoResponseDto(user.getId(), user.getName(), image);
    }
}
