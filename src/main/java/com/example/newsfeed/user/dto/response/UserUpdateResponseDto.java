package com.example.newsfeed.user.dto.response;

import com.example.newsfeed.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class UserUpdateResponseDto {

    private final Long id;
    private final String email;
    private final String name;
    private final List<String> images;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private UserUpdateResponseDto(Long id, String email, String name, List<String> images,
                                  LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.images = images;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserUpdateResponseDto of(User user, List<String> images) {
        return new UserUpdateResponseDto(user.getId(), user.getEmail(), user.getName(), images, user.getCreatedAt(),
                user.getModifiedAt());
    }
}
