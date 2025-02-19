package com.example.newsfeed.board.dto.response;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.comment.dto.response.CommentInfoResponseDto;
import com.example.newsfeed.image.dto.response.ImageResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String contents;
    private final List<ImageResponseDto> images;
    private final List<CommentInfoResponseDto> comments;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public BoardResponseDto(Long id, Long userId, String title, String contents, List<ImageResponseDto> images,
                            List<CommentInfoResponseDto> comments, LocalDateTime createdAt,
                            LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.images = images;
        this.comments = comments;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static BoardResponseDto of(Board board, List<CommentInfoResponseDto> comments) {
        return new BoardResponseDto(board.getId(), board.getUser().getId(), board.getTitle(),
                board.getContents(),
                board.getImages().stream().map(ImageResponseDto::of).toList(), comments,
                board.getCreatedAt(), board.getModifiedAt());
    }
}
