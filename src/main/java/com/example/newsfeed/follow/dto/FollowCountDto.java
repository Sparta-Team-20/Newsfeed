package com.example.newsfeed.follow.dto;

import lombok.Getter;

@Getter
public class FollowCountDto {

    private final Long userId;
    private final Long count;

    public FollowCountDto(Long userId, Long count) {
        this.userId = userId;
        this.count = count;
    }

}
