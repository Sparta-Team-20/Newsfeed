package com.example.newsfeed.board.dto.response;

import com.example.newsfeed.board.entity.Board;
import java.time.LocalDateTime;
import lombok.Getter;


@Getter
public class BoardPageResponseDto {
    private final Long id;
    private final String title;
    private final String contents;
    private final Long commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String userName;


    private BoardPageResponseDto(Long id, String title, String contents, Long commentCount, LocalDateTime createdAt, LocalDateTime modifiedAt, String userName) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userName = userName;
    }

    public static BoardPageResponseDto of(Board board, Long commentCount) {
        return new BoardPageResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContents(),
                commentCount,
                board.getCreatedAt(),
                board.getModifiedAt(),
                board.getUser().getName());
    }
}
