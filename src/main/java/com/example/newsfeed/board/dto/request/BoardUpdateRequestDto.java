package com.example.newsfeed.board.dto.request;

import static com.example.newsfeed.common.consts.Const.CONTENTS_NOT_NULL;
import static com.example.newsfeed.common.consts.Const.CONTENTS_SIZE;
import static com.example.newsfeed.common.consts.Const.IMAGE_NOT_NULL;
import static com.example.newsfeed.common.consts.Const.TITLE_NOT_NULL;
import static com.example.newsfeed.common.consts.Const.TITLE_SIZE;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {

    @NotBlank(message = TITLE_NOT_NULL)
    @Size(max = 20, message = TITLE_SIZE)
    private String title;

    @NotBlank(message = CONTENTS_NOT_NULL)
    @Size(max = 200, message = CONTENTS_SIZE)
    private String contents;

    @NotEmpty(message = IMAGE_NOT_NULL)
    private List<String> images;
}
