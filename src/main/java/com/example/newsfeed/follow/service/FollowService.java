package com.example.newsfeed.follow.service;

import com.example.newsfeed.follow.dto.FollowCountDto;
import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.follow.repository.FollowRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public List<FollowCountDto> countFollowersByUserIds(List<Long> userIds) {
        return followRepository.countFollowersByUserIds(userIds);
    }

    public List<FollowCountDto> countFollowingsByUserIds(List<Long> userIds) {
        return followRepository.countFollowingsByUserIds(userIds);
    }

    public List<Follow> findByFollowerId(Long userId) {
        return followRepository.findByFollowerId(userId);
    }

    public List<Follow> findByFollowingId(Long userId) {
        return followRepository.findByFollowingId(userId);
    }
}
