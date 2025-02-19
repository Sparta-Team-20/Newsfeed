package com.example.newsfeed.user.dto.request;

import com.example.newsfeed.common.consts.Const;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    @NotBlank(message = Const.USERNAME_NOT_NULL)
    @Size(max = 20, message = Const.USERNAME_SIZE)
    private final String name;

    @NotBlank(message = Const.PASSWORD_NOT_NULL)
    @Size(min = 8, max = 20, message = Const.PASSWORD_SIZE)
    @Pattern(regexp = Const.PASSWORD_PATTERN, message = Const.PASSWORD_REQUIREMENT)
    private final String password;

    public UserUpdateRequestDto(String password, String name) {
        this.password = password;
        this.name = name;
    }
}
