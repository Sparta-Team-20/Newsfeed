package com.example.newsfeed.board.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {

    //@NotBlank(message = "댓글 내용은 필수 입력값입니다.")
    private String contents;
}
