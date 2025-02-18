package com.example.newsfeed.common.consts;

public abstract class Const {

    public static final String LOGIN_USER = "LOGIN_USER";

    public static final String EMAIL_SIZE = "이메일은 최대 40자 입니다.";
    public static final String EMAIl_NOT_NULL = "이메일은 필수 값입니다.";
    public static final String EMAIl_TYPE = "이메일 형식에 맞추어야 합니다.";

    public static final String USERNAME_SIZE = "이름은 최대 20자 입니다.";
    public static final String USERNAME_NOT_NULL = "이름은 필수 값입니다.";

    public static final String PASSWORD_SIZE = "비밀번호는 최소 8자에서 최대 20자 입니다.";
    public static final String PASSWORD_NOT_NULL = "비밀번호는 필수 값입니다.";
    public static final String PASSWORD_REQUIREMENT = "비밀번호에는 대소문자 포함 영문, 숫자, 특수문자가 포함되어야 합니다.";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$";

    public static final String IMAGE_URL = "이미지는 URL 형식이어야 합니다.";
    public static final String IMAGE_PATTERN = ".*\\.(png|jpg|jpeg|gif)$";
    public static final String IMAGE_REQUIREMENT = "허용된 확장자는 png, jpg, jpeg, gif 입니다.";

    public static final String TITLE_SIZE = "제목은 최대 20자 입니다.";
    public static final String TITLE_NOT_NULL = "제목은 필수 값입니다.";

    public static final String TODO_CONTENTS_SIZE = "내용은 최대 200자 입니다.";
    public static final String CONTENTS_NOT_NULL = "내용은 필수 값입니다.";

    public static final String COMMENT_CONTENTS_SIZE = "댓글은 최소 1자에서 최대 50자 입니다.";
}