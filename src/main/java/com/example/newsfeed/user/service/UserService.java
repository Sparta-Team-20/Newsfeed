package com.example.newsfeed.user.service;

import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.image.dto.response.UserImageResponse;
import com.example.newsfeed.image.entity.UserImage;
import com.example.newsfeed.user.dto.request.UserSaveRequest;
import com.example.newsfeed.user.dto.request.UserUpdateRequest;
import com.example.newsfeed.user.dto.response.*;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public UserSaveResponse save(UserSaveRequest request) {
        if (!userRepository.existsByEmail(request.getEmail())) {
            //TODO : 예외처리
        }

        //TODO : 비밀번호 암호화
        String encodedPassword = "";
        User user = User.toEntity(request, encodedPassword);
        userRepository.save(user);
        List<UserImageResponse> imageResponseList = new ArrayList<>();
        String[] splitImage = request.getImage().split("/.");
        imageResponseList.add(UserImageResponse.of(splitImage[0], splitImage[1]));
        return UserSaveResponse.of(user, imageResponseList);
    }

    public Page<UserFindAllResponse> findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        List<Long> userIds = userPage.stream()
                .map(User::getId)
                .toList();

        Map<Long, Long>countResults = userPage.stream()
                .collect(Collectors.toMap(User::getId, User::getFollowersSize));
        //TODO : images 구현
        return userPage.map(user -> UserFindAllResponse.of(user, images, countResults.getOrDefault(user.getId(), 0L)));
    }

    public UserFindOneResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                //TODO : 예외처리
        );

        //TODO : 이미지, 팔로우 처리 구현
        List<UserImageResponse> images = new ArrayList<>();
        List<UserInfoResponse> followers = new ArrayList<>();
        return UserFindOneResponse.of(user, images, followers);
    }

    @Transactional
    public UserFindOneResponse follow(Long userId, Long id) {
        if (userRepository.existsByFollowerAndFollowing(userId, id)) {
            //TODO : 팔로우 끊기
            followService.unFollow(userId, id);
            return;
        }

        followService.follow(userId, id);
        return;
    }

    @Transactional
    public UserUpdateResponse update(Long userId, UserUpdateRequest request) {
        User findUser = userRepository.findById(userId).orElseThrow(/* TODO : 예외처리 */);

        // TODO : 비밀번호 암호화
        String encodedPassword = "";
        findUser.update(request, encodedPassword, request.getImages());
        List<UserImageResponse> images = new ArrayList<>();
        return UserUpdateResponse.of(findUser, images);
    }

    @Transactional
    public void delete(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow();
        findUser.delete();
    }
}
