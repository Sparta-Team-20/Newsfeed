package com.example.newsfeed.image.service;

import com.example.newsfeed.image.entity.UserImage;
import com.example.newsfeed.image.repository.UserImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserImageService {

    private final UserImageRepository userImageRepository;

    public void save(UserImage userImage) {
        userImageRepository.save(userImage);
    }
}
