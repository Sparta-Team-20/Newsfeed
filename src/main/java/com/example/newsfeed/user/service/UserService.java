package com.example.newsfeed.user.service;

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

    public UserSaveResponseDto save(UserSaveRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }

        //TODO : 비밀번호 암호화
        String encodedPassword = "";
        User user = User.toEntity(request, request.getPassword());
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
        User user = userRepository.findByIdWithAll(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

        List<ImageResponseDto> images = user.getImages().stream()
                .map(ImageResponseDto::of).toList();

        List<Follow> followers = followService.findByFollowingId(user.getId());
        List<UserInfoResponseDto> userInfoResponseDtos = followers.stream().map(
                follow -> UserInfoResponseDto.of(follow.getFollower())).toList();
        return UserFindOneResponseDto.of(user, images, userInfoResponseDtos);
    }


    @Transactional
    public UserFindOneResponseDto follow(Long userId, Long targetUserId) {
        User user = userRepository.findByIdWithAll(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        User targetUser = userRepository.findByIdWithAll(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우할 사용자를 찾을 수 없습니다."));

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
        User findUser = userRepository.findByIdWithAll(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // TODO : 비밀번호 암호화
        String encodedPassword = "";
        findUser.update(request, encodedPassword, request.getImages());
        List<ImageResponseDto> images = new ArrayList<>();
        return UserUpdateResponseDto.of(findUser, images);
    }

    @Transactional
    public void delete(Long userId) {
        User findUser = userRepository.findByIdWithAll(userId).orElseThrow();
        findUser.delete();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

}
