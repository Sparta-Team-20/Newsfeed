package com.example.newsfeed.image.dto.response;

import com.example.newsfeed.image.dto.request.UserImageRequestDto;
import com.example.newsfeed.image.entity.UserImage;
import lombok.Getter;

@Getter
public class UserImageResponseDto {

    private final String imageUrl;
    private final String imageType;

    public UserImageResponseDto(String imageUrl, String imageType) {
        this.imageUrl = imageUrl;
        this.imageType = imageType;
    }

    public static UserImageResponseDto of(UserImageRequestDto userImage) {
        return new UserImageResponseDto(userImage.getImageUrl(), userImage.getImageType());
    }

    public static UserImageResponseDto of(UserImage userImage) {
        return new UserImageResponseDto(userImage.getImageUrl(), userImage.getImageType());
    }
}
