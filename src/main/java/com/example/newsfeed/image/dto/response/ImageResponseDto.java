package com.example.newsfeed.image.dto.response;

import com.example.newsfeed.image.entity.BoardImage;
import com.example.newsfeed.image.entity.UserImage;
import lombok.Getter;

@Getter
public class ImageResponseDto {

    private final String imageUrl;
    private final String imageType;

    public ImageResponseDto(String imageUrl, String imageType) {
        this.imageUrl = imageUrl;
        this.imageType = imageType;
    }

    public static ImageResponseDto of(UserImage userImage) {
        return new ImageResponseDto(userImage.getImageUrl(), userImage.getImageType());
    }

    public static ImageResponseDto of(BoardImage boardImage) {
        return new ImageResponseDto(boardImage.getImageUrl(), boardImage.getImageType());
    }
}
