package com.example.newsfeed.comment.dto.request;

import com.example.newsfeed.common.consts.Const;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentSaveRequestDto {
    @NotBlank(message = Const.CONTENTS_NOT_NULL)
    @Size(min = 1, max = 50, message = Const.COMMENT_CONTENTS_SIZE)
    private final String contents;

    public CommentSaveRequestDto(String contents) {
        this.contents = contents;
    }
}
