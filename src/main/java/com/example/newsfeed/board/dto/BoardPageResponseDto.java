package com.example.newsfeed.board.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class BoardPageResponseDto {
    private final Long id;
    private final String title;
    private final String contents;
    private final int commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String userName;

    public BoardPageResponseDto(Long id, String title, String contents, int commentCount, LocalDateTime createdAt, LocalDateTime modifiedAt, String userName) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userName = userName;
    }
}
