package com.example.newsfeed.user.dto.response;

import com.example.newsfeed.image.dto.response.UserImageResponseDto;
import com.example.newsfeed.image.entity.UserImage;
import com.example.newsfeed.user.entity.User;
import java.util.List;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

    private final Long id;
    private final String name;
    private final UserImageResponseDto image;

    private UserInfoResponseDto(Long id, String name, UserImageResponseDto image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public static UserInfoResponseDto of(User user) {
        List<UserImage> images = user.getImages();
        UserImageResponseDto imageDto = (images != null && !images.isEmpty())
                ? UserImageResponseDto.of(images.get(0))
                : null;
        return new UserInfoResponseDto(user.getId(), user.getName(), imageDto);
    }
}
