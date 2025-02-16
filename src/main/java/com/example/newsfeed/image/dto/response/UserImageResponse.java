package com.example.newsfeed.image.dto.response;

import lombok.Getter;

@Getter
public class UserImageResponse {

    private final String imageUrl;
    private final String imageType;

    public UserImageResponse(String imageUrl, String imageType) {
        this.imageUrl = imageUrl;
        this.imageType = imageType;
    }

    public static UserImageResponse of(String imageUrl, String imageType) {
        return new UserImageResponse(imageUrl, imageType);
    }
}
