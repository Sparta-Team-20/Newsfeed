package com.example.newsfeed.user.service;

import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.image.dto.response.UserImageResponseDto;
import com.example.newsfeed.image.entity.UserImage;
import com.example.newsfeed.user.dto.request.UserSaveRequestDto;
import com.example.newsfeed.user.dto.request.UserUpdateRequestDto;
import com.example.newsfeed.user.dto.response.*;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserSaveResponseDto save(UserSaveRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }

        //TODO : 비밀번호 암호화
        String encodedPassword = "";
        User user = User.toEntity(request, request.getPassword());
        userRepository.save(user);
        List<UserImageResponseDto> imageResponseList = new ArrayList<>();
        imageResponseList.add(UserImageResponseDto.of(request.getImage()));
        return UserSaveResponseDto.of(user, imageResponseList);
    }

    public List<UserFindAllResponseDto> findAll() {
        List<User> userList = userRepository.findAllBy();

        Map<Long, Long> countResults = userList.stream()
                .collect(Collectors.toMap(User::getId, User::getFollowersSize));

        return userList.stream().map(user -> UserFindAllResponseDto.of(user, user.getImages(),
                countResults.getOrDefault(user.getId(), 0L))).toList();
    }

    public UserFindOneResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

        List<UserImageResponseDto> images = user.getImages().stream()
                .map(UserImageResponseDto::of).toList();
        List<UserInfoResponseDto> followers = user.getFollowers().stream()
                .map(follow -> UserInfoResponseDto.of(follow.getFollower()))
                .collect(Collectors.toList());
        return UserFindOneResponseDto.of(user, images, followers);
    }

    @Transactional
    public UserFindOneResponseDto follow(Long userId, Long targetUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        User targetUser = userRepository.findById(targetUserId)
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

        List<UserImageResponseDto> images = user.getImages().stream()
                .map(UserImageResponseDto::of).toList();
        List<UserInfoResponseDto> followers = user.getFollowers().stream()
                .map(follow -> UserInfoResponseDto.of(follow.getFollower()))
                .collect(Collectors.toList());

        return UserFindOneResponseDto.of(user, images, followers);
    }

    @Transactional
    public UserUpdateResponseDto update(Long userId, UserUpdateRequestDto request) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // TODO : 비밀번호 암호화
        String encodedPassword = "";
        findUser.update(request, encodedPassword, request.getImages());
        List<UserImageResponseDto> images = new ArrayList<>();
        return UserUpdateResponseDto.of(findUser, images);
    }

    @Transactional
    public void delete(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow();
        findUser.delete();
    }
}
