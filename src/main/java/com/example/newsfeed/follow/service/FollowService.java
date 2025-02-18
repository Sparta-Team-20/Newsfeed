package com.example.newsfeed.follow.service;

import com.example.newsfeed.follow.dto.FollowCountDto;
import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.follow.repository.FollowRepository;
import java.util.List;
import java.util.Optional;
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

    public Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId) {
        return followRepository.findByFollowerIdAndFollowingId(followerId, followingId);
    }

    public void delete(Follow follow) {
        followRepository.delete(follow);
    }

    public void save(Follow follow) {
        followRepository.save(follow);
    }

    public void flush() {
        followRepository.flush();
    }
}
