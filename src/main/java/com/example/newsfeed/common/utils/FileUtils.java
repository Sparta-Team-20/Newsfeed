package com.example.newsfeed.common.utils;

import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    public static String[] splitFileName(MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null) {
            return new String[]{"", ""};
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + extension;

        return new String[]{newFileName, extension};
    }
}
