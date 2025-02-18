package com.example.newsfeed.image.dto.request;

import lombok.Getter;

@Getter
public class ImageRequestDto {

    private final String imageUrl;
    private final String imageType;

    public ImageRequestDto(String imageUrl, String imageType) {
        this.imageUrl = imageUrl;
        this.imageType = imageType;
    }
}
