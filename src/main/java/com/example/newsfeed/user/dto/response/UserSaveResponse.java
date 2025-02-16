package com.example.newsfeed.user.dto.response;


import com.example.newsfeed.image.dto.response.UserImageResponse;
import com.example.newsfeed.image.entity.UserImage;
import com.example.newsfeed.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserSaveResponse {

    private final Long id;
    private final String email;
    private final String name;
    private final List<UserImageResponse> images;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private UserSaveResponse(Long id, String email, String name, List<UserImageResponse> images, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.images = images;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserSaveResponse of(User user, List<UserImageResponse> images) {
        return new UserSaveResponse(user.getId(), user.getEmail(), user.getName(), images, user.getCreatedAt(), user.getModifiedAt());
    }
}
