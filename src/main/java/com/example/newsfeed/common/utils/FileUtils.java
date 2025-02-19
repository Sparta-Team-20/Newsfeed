package com.example.newsfeed.common.utils;

public class FileUtils {

    public static String[] splitFileName(String file) {
        if (file == null) {
            return new String[]{"", ""};
        }

        String extension = file.substring(file.lastIndexOf("."));
        String newFileName = file.substring(0, file.lastIndexOf("."));

        return new String[]{newFileName, extension};
    }
}
