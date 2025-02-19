package com.example.newsfeed.board.dto.response;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.image.dto.response.ImageResponseDto;
import com.example.newsfeed.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class BoardUpdateResponseDto {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String contents;
    private final List<ImageResponseDto> images;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private BoardUpdateResponseDto(Long id, Long userId, String title, String contents, List<ImageResponseDto> images, LocalDateTime createdAt,
                                   LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.images = images;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static BoardUpdateResponseDto of(Board board, User user, List<ImageResponseDto> images) {
        return new BoardUpdateResponseDto(board.getId(), user.getId(), board.getTitle(), board.getContents(), images,
                board.getCreatedAt(), board.getModifiedAt());
    }
}
