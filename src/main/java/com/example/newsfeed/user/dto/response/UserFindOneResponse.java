package com.example.newsfeed.user.dto.response;

import com.example.newsfeed.image.dto.response.UserImageResponse;
import com.example.newsfeed.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserFindOneResponse {

    private final Long id;
    private final String email;
    private final String name;
    private final List<UserImageResponse> images;
    private final List<UserInfoResponse> followers;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private UserFindOneResponse(Long id, String email, String name, List<UserImageResponse> images, List<UserInfoResponse> followers, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.images = images;
        this.followers = followers;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserFindOneResponse of(User user, List<UserImageResponse> images, List<UserInfoResponse> followers) {
        return new UserFindOneResponse(user.getId(), user.getEmail(), user.getName(), images, followers, user.getCreatedAt(), user.getModifiedAt());
    }
}
