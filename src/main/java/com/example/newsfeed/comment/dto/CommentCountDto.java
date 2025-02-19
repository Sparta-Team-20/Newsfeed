package com.example.newsfeed.comment.dto;

import lombok.Getter;

@Getter
public class CommentCountDto {

    private final Long boardId;
    private final Long count;

    public CommentCountDto(Long boardId, Long count) {
        this.boardId = boardId;
        this.count = count;
    }
}