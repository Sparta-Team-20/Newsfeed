package com.example.newsfeed.image.dto;

import lombok.Getter;

@Getter
public class UserImageDto {

    private final Long userId;
    private final String imageUrl;
    private final String imageType;

    public UserImageDto(Long userId, String imageUrl, String imageType) {
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.imageType = imageType;
    }
}
