package com.example.newsfeed.user.service;

import com.example.newsfeed.user.dto.request.UserSaveRequest;
import com.example.newsfeed.user.dto.request.UserUpdateRequest;
import com.example.newsfeed.user.dto.response.UserFindAllResponse;
import com.example.newsfeed.user.dto.response.UserFindOneResponse;
import com.example.newsfeed.user.dto.response.UserSaveResponse;
import com.example.newsfeed.user.dto.response.UserUpdateResponse;
import com.example.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserSaveResponse save(UserSaveRequest request) {
        return null;
    }

    public Page<UserFindAllResponse> findAll(Pageable pageable) {
    }

    public UserFindOneResponse findById(Long id) {
    }

    public UserFindOneResponse follow(Long userId, Long id) {
    }

    public UserUpdateResponse update(Long userId, UserUpdateRequest request) {
    }

    public void delete(Long userId) {
    }
}
