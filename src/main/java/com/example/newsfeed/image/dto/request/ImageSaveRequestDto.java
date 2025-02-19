package com.example.newsfeed.image.dto.request;

import lombok.Getter;

@Getter
public class ImageSaveRequestDto {

    private final String image;

    public ImageSaveRequestDto(String image) {
        this.image = image;
    }

}
