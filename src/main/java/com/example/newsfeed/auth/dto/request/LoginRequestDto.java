package com.example.newsfeed.auth.dto.request;

import com.example.newsfeed.common.consts.Const;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotBlank(message = Const.EMAIl_NOT_NULL)
    @Email(message = Const.EMAIl_TYPE)
    @Size(max = 40, message = Const.EMAIL_SIZE)
    private final String email;

    @NotBlank(message = Const.PASSWORD_NOT_NULL)
    @Size(min = 8, max = 20, message = Const.PASSWORD_SIZE)
    @Pattern(regexp = Const.PASSWORD_PATTERN, message = Const.PASSWORD_REQUIREMENT)
    private final String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
