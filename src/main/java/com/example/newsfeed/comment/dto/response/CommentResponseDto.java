package com.example.newsfeed.comment.dto.response;

import com.example.newsfeed.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final Long userId;
    private final Long boardId;
    private final String content;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    private CommentResponseDto(Long id, Long userId, Long boardId, String content, LocalDateTime createAt,
                               LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.boardId = boardId;
        this.content = content;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentResponseDto of(Comment comment) {
        return new CommentResponseDto(comment.getId(),
                comment.getUser().getId(),
                comment.getBoard().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getCreatedAt()
        );
    }
}
