package com.example.newsfeed.user.dto.response;


import com.example.newsfeed.image.dto.response.ImageResponseDto;
import com.example.newsfeed.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserSaveResponseDto {

    private final Long id;
    private final String email;
    private final String name;
    private final List<ImageResponseDto> images;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private UserSaveResponseDto(Long id, String email, String name, List<ImageResponseDto> images,
                                LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.images = images;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserSaveResponseDto of(User user, List<ImageResponseDto> images) {
        return new UserSaveResponseDto(user.getId(), user.getEmail(), user.getName(), images, user.getCreatedAt(),
                user.getModifiedAt());
    }
}
