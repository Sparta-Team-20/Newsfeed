package com.example.newsfeed.image.service;

import com.example.newsfeed.common.utils.FileUtils;
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
    
    // 유저 이미지 저장
    public void save(UserImage userImage) {
        userImageRepository.save(userImage);
    }
    
    // 유저 이미지 삭제
    public void delete(UserImage image) {
        userImageRepository.delete(image);
    }
    
    // 유저 이미지 단건 조회
    public Optional<UserImage> findById(Long imageId) {
        return userImageRepository.findById(imageId);
    }

    // 유저의 가장 최근 이미지 1개 조회
    public List<UserImage> findLatestImagesPerUser(List<Long> userId) {
        return userImageRepository.findLatestImagesPerUser(userId);
    }
    
    // 유저의 모든 이미지 조회
    public List<UserImage> findAllByUserId(Long id){
        return userImageRepository.findAllByUserId(id);
    }
    
    // 유저의 이미지 String 형식으로 변환
    public List<String> getUserImages(Long userId) {
        return findAllByUserId(userId)
                .stream()
                .map(FileUtils::joinFileName)
                .toList();
    }
}
