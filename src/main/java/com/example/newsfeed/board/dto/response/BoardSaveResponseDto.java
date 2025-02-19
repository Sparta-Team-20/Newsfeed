package com.example.newsfeed.board.dto.response;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class BoardSaveResponseDto {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String contents;
    private final List<String> images;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private BoardSaveResponseDto(Long id, Long userId, String title, String contents, List<String> images, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.images = images;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static BoardSaveResponseDto of(Board board, User user, List<String> images) {
        return new BoardSaveResponseDto(board.getId(), user.getId(), board.getTitle(), board.getContents(), images,
                board.getCreatedAt(), board.getModifiedAt());
    }
}
