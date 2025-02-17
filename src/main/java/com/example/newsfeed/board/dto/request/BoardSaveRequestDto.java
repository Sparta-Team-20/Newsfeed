package com.example.newsfeed.board.dto.request;

import com.example.newsfeed.image.entity.BoardImage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardSaveRequestDto {

    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max = 10, message = "제목은 10글자 이내여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String contents;

    private List<BoardImage> images;
}
