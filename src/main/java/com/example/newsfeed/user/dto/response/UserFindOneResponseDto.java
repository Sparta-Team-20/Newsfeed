package com.example.newsfeed.user.dto.response;

import com.example.newsfeed.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class UserFindOneResponseDto {

    private final Long id;
    private final String email;
    private final String name;
    private final List<String> images;
    private final List<UserInfoResponseDto> followers;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private UserFindOneResponseDto(Long id, String email, String name, List<String> images,
                                   List<UserInfoResponseDto> followers, LocalDateTime createdAt,
                                   LocalDateTime modifiedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.images = images;
        this.followers = followers;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserFindOneResponseDto of(User user, List<String> images,
                                            List<UserInfoResponseDto> followers) {
        return new UserFindOneResponseDto(user.getId(), user.getEmail(), user.getName(), images, followers,
                user.getCreatedAt(), user.getModifiedAt());
    }
}
