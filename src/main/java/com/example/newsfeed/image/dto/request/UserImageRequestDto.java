package com.example.newsfeed.image.dto.request;

import lombok.Getter;

@Getter
public class UserImageRequestDto {

    private final String imageUrl;
    private final String imageType;

    public UserImageRequestDto(String imageUrl, String imageType) {
        this.imageUrl = imageUrl;
        this.imageType = imageType;
    }
}
