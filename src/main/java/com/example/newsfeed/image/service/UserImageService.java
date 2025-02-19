package com.example.newsfeed.image.service;

import com.example.newsfeed.image.entity.UserImage;
import com.example.newsfeed.image.repository.UserImageRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserImageService {

    private final UserImageRepository userImageRepository;

    public void save(UserImage userImage) {
        userImageRepository.save(userImage);
    }

    public void update(List<UserImage> images) {

    }

    public void delete(UserImage image) {
        userImageRepository.delete(image);
    }

    public Optional<UserImage> findById(Long imageId) {
        return userImageRepository.findById(imageId);
    }

    public List<UserImage> findLatestImagesPerUser(List<Long> userId) {
        return userImageRepository.findLatestImagesPerUser(userId);
    }

    public List<UserImage> findAllByUserId(Long id){
        return userImageRepository.findAllByUserId(id);
    }
}
