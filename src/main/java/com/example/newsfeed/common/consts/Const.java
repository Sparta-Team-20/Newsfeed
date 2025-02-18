package com.example.newsfeed.common.consts;

public abstract class Const {

    public static final String LOGIN_USER = "LOGIN_USER";

    public static final String TITLE_NOT_NULL = "제목은 필수 입력값입니다.";
    public static final String TITLE_SIZE = "제목은 최대 20글자 입니다.";

    public static final String CONTENTS_NOT_NULL = "내용은 필수 입력값입니다.";
    public static final String CONTENTS_SIZE = "내용은 최대 200자 입니다.";

    public static final String IMAGES_NOT_NULL = "이미지는 필수 입력값입니다.";
    public static final String IMAGES_PATTERN = ".*\\.(png|jpg|jpeg|gif)$";
    public static final String IMAGES_PATTERN__REQUIREMENT = "허용된 확장자는 png, jpg, jpeg, gif 입니다.";


}