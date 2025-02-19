package com.example.newsfeed.board.dto.response;

import com.example.newsfeed.board.entity.Board;
import java.time.LocalDateTime;
import lombok.Getter;


@Getter
public class BoardPageResponseDto {
    private final Long id;
    private final String userName;
    private final String title;
    private final String contents;
    private final Long commentCount;
    private final String images;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    private BoardPageResponseDto(Long id, String userName, String title, String contents, Long commentCount,
                                 String images, LocalDateTime createdAt,
                                 LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userName = userName;
        this.images = images;
    }

    public static BoardPageResponseDto of(Board board, Long commentCount, String images) {
        return new BoardPageResponseDto(
                board.getId(),
                board.getUser().getName(),
                board.getTitle(),
                board.getContents(),
                commentCount,
                images,
                board.getCreatedAt(),
                board.getModifiedAt()
        );
    }
}
