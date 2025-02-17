package com.example.newsfeed.board.dto.response;

import com.example.newsfeed.board.entity.Board;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class BoardUpdateResponseDto {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private BoardUpdateResponseDto(Long id, Long userId, String title, String contents, LocalDateTime createdAt,
                                   LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static BoardUpdateResponseDto of(Board board) {
        return new BoardUpdateResponseDto(board.getId(), board.getUser().getId(), board.getTitle(), board.getContents(),
                board.getCreatedAt(), board.getModifiedAt());
    }
}
