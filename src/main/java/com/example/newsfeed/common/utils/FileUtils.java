package com.example.newsfeed.common.utils;

import com.example.newsfeed.image.entity.BoardImage;
import com.example.newsfeed.image.entity.UserImage;

public class FileUtils {

    public static String[] splitFileName(String file) {
        if (file == null || file.isBlank() || file.isEmpty()) {
            return new String[]{"none", ".png"};
        }

        String extension = file.substring(file.lastIndexOf("."));
        String newFileName = file.substring(0, file.lastIndexOf("."));

        return new String[]{newFileName, extension};
    }

    public static String joinFileName(UserImage userImage) {
        if (userImage.getImageUrl() == null || userImage.getImageUrl().isEmpty()) {
            return userImage.getImageType();
        }
        if (userImage.getImageType() == null || userImage.getImageType().isEmpty()) {
            return userImage.getImageUrl();
        }
        return userImage.getImageUrl() + userImage.getImageType();
    }

    public static String joinFileName(BoardImage boardImage) {
        if (boardImage.getImageUrl() == null || boardImage.getImageUrl().isEmpty()) {
            return boardImage.getImageType();
        }
        if (boardImage.getImageType() == null || boardImage.getImageType().isEmpty()) {
            return boardImage.getImageUrl();
        }
        return boardImage.getImageUrl() + boardImage.getImageType();
    }

}