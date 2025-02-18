package com.example.newsfeed.image.dto.request;

import com.example.newsfeed.common.consts.Const;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
public class UserImageRequestDto {

    @NotEmpty
    @URL(message = Const.IMAGE_URL)
    private final String imageUrl;

    @Pattern(regexp = Const.IMAGE_PATTERN, message = Const.IMAGE_REQUIREMENT)
    private final String imageType;

    public UserImageRequestDto(String imageUrl, String imageType) {
        this.imageUrl = imageUrl;
        this.imageType = imageType;
    }
}
