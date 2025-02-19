package com.example.newsfeed.user.dto.response;

import com.example.newsfeed.image.dto.response.ImageResponseDto;
import com.example.newsfeed.user.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

    private final Long id;
    private final String name;
    private final ImageResponseDto image;

    private UserInfoResponseDto(Long id, String name, ImageResponseDto image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public static UserInfoResponseDto of(User user, ImageResponseDto imageDto) {
        return new UserInfoResponseDto(user.getId(), user.getName(), imageDto);
    }
}
