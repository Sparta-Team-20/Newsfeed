package com.example.newsfeed.user.service;

import com.example.newsfeed.common.config.PasswordEncoder;
import com.example.newsfeed.common.exception.CustomExceptionHandler;
import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.follow.dto.FollowCountDto;
import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.follow.service.FollowService;
import com.example.newsfeed.image.dto.response.ImageResponseDto;
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
    private final PasswordEncoder passwordEncoder;

    public UserSaveResponseDto save(UserSaveRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomExceptionHandler(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.toEntity(request, encodedPassword);
        UserImage userImage = UserImage.toEntity(user, request.getImage());
        userRepository.save(user);
        userImageService.save(userImage);
        List<ImageResponseDto> imageResponseList = new ArrayList<>();
        imageResponseList.add(ImageResponseDto.of(userImage));
        return UserSaveResponseDto.of(user, imageResponseList);
    }

    public List<UserFindAllResponseDto> findAll() {
        List<User> users = userRepository.findAllBy();
        List<Long> userIds = users.stream()
                .map(User::getId)
                .toList();

        List<FollowCountDto> countResults = followService.countFollowingsByUserIds(userIds);

        Map<Long, Long> followerCountMap = countResults.stream()
                .collect(Collectors.toMap(FollowCountDto::getUserId, FollowCountDto::getCount));
        Map<Long, UserImage> userImageMap = users.stream()
                .collect(Collectors.toMap(User::getId, User::getFirstImage));
        return users.stream().map(user -> UserFindAllResponseDto.of(user, userImageMap.get(user.getId()),
                followerCountMap.getOrDefault(user.getId(), 0L))).toList();
    }

    public UserFindOneResponseDto findOne(Long id) {
        User user = userRepository.findUsersById(id)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_USER));

        List<ImageResponseDto> images = user.getImages().stream()
                .map(ImageResponseDto::of).toList();

        List<Follow> followers = followService.findByFollowingId(user.getId());
        List<UserInfoResponseDto> userInfoResponseDtos = followers.stream().map(
                follow -> UserInfoResponseDto.of(follow.getFollower())).toList();
        return UserFindOneResponseDto.of(user, images, userInfoResponseDtos);
    }


    @Transactional
    public UserFindOneResponseDto follow(Long userId, Long targetUserId) {
        User user = userRepository.findUsersById(userId)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_USER));
        User targetUser = userRepository.findUsersById(targetUserId)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_FOLLOW_USER));

        boolean isFollowing = user.getFollowings().stream()
                .anyMatch(follow -> follow.getFollowing().getId().equals(targetUserId));

        if (isFollowing) {
            user.getFollowings().removeIf(follow -> follow.getFollowing().getId().equals(targetUserId));
            targetUser.getFollowers().removeIf(follow -> follow.getFollower().getId().equals(userId));
        } else {
            Follow follow = Follow.toEntity(user, targetUser);
            user.getFollowings().add(follow);
            targetUser.getFollowers().add(follow);
        }

        List<ImageResponseDto> images = user.getImages().stream()
                .map(ImageResponseDto::of).toList();

        List<Follow> followers = followService.findByFollowingId(user.getId());
        List<UserInfoResponseDto> userInfoResponseDtos = followers.stream().map(
                follow -> UserInfoResponseDto.of(follow.getFollower())).toList();

        return UserFindOneResponseDto.of(user, images, userInfoResponseDtos);
    }

    @Transactional
    public UserUpdateResponseDto update(Long userId, UserUpdateRequestDto request) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_USER));

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        findUser.update(request, encodedPassword, request.getImages());
        List<ImageResponseDto> images = new ArrayList<>();
        return UserUpdateResponseDto.of(findUser, images);
    }

    @Transactional
    public void delete(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow();
        findUser.delete();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_USER));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
