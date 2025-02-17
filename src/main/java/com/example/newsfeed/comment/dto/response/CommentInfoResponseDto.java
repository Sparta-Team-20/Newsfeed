package com.example.newsfeed.comment.dto.response;

import com.example.newsfeed.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentInfoResponseDto {
    private final Long id;
    private final Long userId;
    private final String content;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    public static CommentInfoResponseDto of(Comment comment) {
        return new CommentInfoResponseDto(comment.getId(), comment.getUser().getId(), comment.getContent(),
                comment.getCreatedAt(), comment.getModifiedAt());
    }
}
