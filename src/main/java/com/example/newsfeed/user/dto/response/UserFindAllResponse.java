package com.example.newsfeed.user.dto.response;

import com.example.newsfeed.image.dto.response.UserImageResponse;
import com.example.newsfeed.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserFindAllResponse {

    private final Long id;
    private final String email;
    private final String name;
    private final UserImageResponse image;
    private final Integer followerCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private UserFindAllResponse(Long id, String email, String name, UserImageResponse image, Integer followerCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.image = image;
        this.followerCount = followerCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserFindAllResponse of(User user, UserImageResponse image, Integer followerCount) {
        return new UserFindAllResponse(user.getId(), user.getEmail(), user.getName(), image, followerCount, user.getCreatedAt(), user.getModifiedAt());
    }
}
