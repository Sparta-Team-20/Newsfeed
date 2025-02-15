package com.example.newsfeed.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentSaveResponseDto {
    private final Long id;
    private final Long userId;
    private final Long boardId;
    private final String content;
    private final String createAt;
    private final String modifiedAt;
}
