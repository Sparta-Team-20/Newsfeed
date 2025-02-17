package com.example.newsfeed.user.dto.response;

import com.example.newsfeed.image.dto.response.UserImageResponseDto;
import com.example.newsfeed.image.entity.UserImage;
import com.example.newsfeed.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class UserFindAllResponseDto {

    private final Long id;
    private final String email;
    private final String name;
    private final UserImageResponseDto image;
    private final Long followerCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private UserFindAllResponseDto(Long id, String email, String name, UserImageResponseDto image, Long followerCount,
                                   LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.image = image;
        this.followerCount = followerCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserFindAllResponseDto of(User user, UserImage image, Long followerCount) {
        UserImageResponseDto imageDto = UserImageResponseDto.of(image);
        return new UserFindAllResponseDto(user.getId(), user.getEmail(), user.getName(), imageDto, followerCount,
                user.getCreatedAt(), user.getModifiedAt());
    }
}
