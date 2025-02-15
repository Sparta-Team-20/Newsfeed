package com.example.newsfeed.board.dto;

import lombok.Getter;

import java.awt.*;
import java.util.List;

@Getter
public class BoardResponseDto {
    private final Long id;
    private final String title;
    private final String contents;
    private final List<Image> image;

    public BoardResponseDto(Long id, String title, String contents, List<Image> image) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.image = image;
    }
}
