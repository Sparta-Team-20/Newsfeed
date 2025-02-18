package com.example.newsfeed.comment.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentUpdateResponseDto {
    private final Long id;
    private final Long userId;
    private final Long boardId;
    private final String content;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;
}
