package com.example.newsfeed.board.dto.response;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.image.dto.response.ImageResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class BoardFindAllResponseDto {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String contents;
    private final List<ImageResponseDto> images;
    private final Long commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public BoardFindAllResponseDto(Long id, Long userId, String title, String contents, List<ImageResponseDto> images, Long commentCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.images = images;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static BoardFindAllResponseDto of(Board board, Long commentCount) {
        return new BoardFindAllResponseDto(board.getId(), board.getUser().getId(), board.getTitle(), board.getContents(),
                board.getImages().stream().map(ImageResponseDto::of).toList(), commentCount, board.getCreatedAt(),
                board.getModifiedAt());
    }
}
