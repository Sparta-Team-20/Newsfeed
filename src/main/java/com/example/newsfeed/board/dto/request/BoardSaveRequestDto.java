package com.example.newsfeed.board.dto.request;

import com.example.newsfeed.image.entity.BoardImage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.newsfeed.common.consts.Const.*;

@Getter
@NoArgsConstructor
public class BoardSaveRequestDto {

    @NotBlank(message = TITLE_NOT_NULL)
    @Size(max = 20, message = TITLE_SIZE)
    private String title;

    @NotBlank(message = CONTENTS_NOT_NULL)
    @Size(max = 200, message = CONTENTS_SIZE)
    private String contents;

    @NotEmpty(message = IMAGES_NOT_NULL)
    private List<BoardImage> images;
}
