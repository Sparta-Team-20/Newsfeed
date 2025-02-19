package com.example.newsfeed.user.service;

import com.example.newsfeed.board.service.BoardDeleteService;
import com.example.newsfeed.comment.service.CommentDeleteService;
import com.example.newsfeed.common.config.PasswordEncoder;
import com.example.newsfeed.common.exception.CustomExceptionHandler;
import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.common.utils.FileUtils;
import com.example.newsfeed.follow.dto.FollowCountDto;
import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.follow.service.FollowService;
import com.example.newsfeed.image.dto.request.ImageSaveRequestDto;
import com.example.newsfeed.image.entity.UserImage;
import com.example.newsfeed.image.service.UserImageService;
import com.example.newsfeed.user.dto.request.UserSaveRequestDto;
import com.example.newsfeed.user.dto.request.UserUpdateRequestDto;
import com.example.newsfeed.user.dto.response.UserFindAllResponseDto;
import com.example.newsfeed.user.dto.response.UserFindOneResponseDto;
import com.example.newsfeed.user.dto.response.UserInfoResponseDto;
import com.example.newsfeed.user.dto.response.UserSaveResponseDto;
import com.example.newsfeed.user.dto.response.UserUpdateResponseDto;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserImageService userImageService;
    private final FollowService followService;
    private final BoardDeleteService boardDeleteService;
    private final CommentDeleteService commentDeleteService;
    private final PasswordEncoder passwordEncoder;

    // 유저 저장
    public UserSaveResponseDto save(UserSaveRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomExceptionHandler(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.toEntity(request, encodedPassword);
        UserImage userImage = UserImage.toEntity(user, request.getImage());
        userRepository.save(user);
        userImageService.save(userImage);
        List<String> images = new ArrayList<>();
        images.add(FileUtils.joinFileName(userImage));
        return UserSaveResponseDto.of(user, images);
    }
    
    // 유저 전체 조회
    public List<UserFindAllResponseDto> findAll() {
        List<User> users = userRepository.findAll();
        List<Long> userIds = users.stream()
                .map(User::getId)
                .toList();

        List<FollowCountDto> countResults = followService.countFollowingsByUserIds(userIds);

        Map<Long, Long> followerCountMap = countResults.stream()
                .collect(Collectors.toMap(FollowCountDto::getUserId, FollowCountDto::getCount));

        List<UserImage> userImageList = userImageService.findLatestImagesPerUser(userIds);

        Map<Long, UserImage> userImageMap = userImageList.stream().collect(
                Collectors.toMap(userImage -> userImage.getUser().getId(), userImage -> userImage, (a, b) -> b));

        return users.stream().map(user -> UserFindAllResponseDto.of(user, userImageMap.get(user.getId()),
                followerCountMap.getOrDefault(user.getId(), 0L))).toList();
    }
    
    // 유저 단건 조회
    public UserFindOneResponseDto findOne(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_USER));

        List<String> images = userImageService.getUserImages(id);

        List<UserInfoResponseDto> userInfoResponseDtos = getUserInfoResponses(user);
        return UserFindOneResponseDto.of(user, images, userInfoResponseDtos);
    }
    
    // 유저 팔로우 추가 or 취소
    @Transactional
    public UserFindOneResponseDto follow(Long userId, Long targetUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_USER));
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_FOLLOW_USER));

        Optional<Follow> existingFollow = followService.findByFollowerIdAndFollowingId(userId, targetUserId);

        if (existingFollow.isPresent()) {
            Follow followToRemove = existingFollow.get();
            followService.delete(followToRemove);
        } else {
            Follow follow = Follow.toEntity(user, targetUser);
            followService.save(follow);
        }

        List<String> images = userImageService.getUserImages(userId);

        List<UserInfoResponseDto> userInfoResponseDtos = getUserInfoResponses(user);
        return UserFindOneResponseDto.of(user, images, userInfoResponseDtos);
    }
    
    // 유저 정보 수정
    @Transactional
    public UserUpdateResponseDto update(Long userId, UserUpdateRequestDto request) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_USER));
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        findUser.update(request, encodedPassword);

        List<String> images = userImageService.getUserImages(userId);

        return UserUpdateResponseDto.of(findUser, images);
    }
    
    // 유저 탈퇴
    @Transactional
    public void delete(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow();
        findUser.delete();
        boardDeleteService.delete(findUser);
        commentDeleteService.delete(findUser);
    }
    
    // 유저 이미지 추가
    @Transactional
    public UserUpdateResponseDto addImage(Long userId, ImageSaveRequestDto requestDto) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_USER));

        userImageService.save(UserImage.toEntity(findUser, requestDto.getImage()));

        List<String> images = userImageService.getUserImages(userId);

        return UserUpdateResponseDto.of(findUser, images);
    }
    
    // 유저 이미지 삭제
    @Transactional
    public UserUpdateResponseDto deleteImage(Long userId, Long imageId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_USER));

        UserImage userImage = userImageService.findById(imageId)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_IMAGE));
        userImageService.delete(userImage);

        List<String> images = userImageService.getUserImages(userId);

        return UserUpdateResponseDto.of(findUser, images);
    }
    
    // 유저 단건 조회
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_USER));
    }

    // 유저 단건조회(이메일 사용)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // List형식의 UserInfoResponse 만들기 위한 메서드
    public List<UserInfoResponseDto> getUserInfoResponses(User user) {
        List<Follow> followers = followService.findByFollowingId(user.getId());

        List<Long> followerIds = followers.stream()
                .map(follow -> follow.getFollower().getId())
                .toList();

        List<UserImage> followerImageList = userImageService.findLatestImagesPerUser(followerIds);

        Map<Long, UserImage> userImageMap = new HashMap<>();
        for (UserImage userImage : followerImageList) {
            userImageMap.put(userImage.getUser().getId(), userImage);
        }

        return followers.stream()
                .map(follow -> UserInfoResponseDto.of(
                        follow.getFollower(),
                        FileUtils.joinFileName(userImageMap.get(follow.getFollower().getId()))
                ))
                .toList();
    }
}
