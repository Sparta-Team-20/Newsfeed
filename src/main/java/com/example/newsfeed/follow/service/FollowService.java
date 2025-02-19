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

    // 각 유저의 팔로워 수 반환
    public List<FollowCountDto> countFollowingsByUserIds(List<Long> userIds) {
        return followRepository.countFollowingsByUserIds(userIds);
    }
    
    // 해당 유저의 팔로워들 반환
    public List<Follow> findByFollowingId(Long userId) {
        return followRepository.findByFollowingId(userId);
    }
    
    // 팔로워, 팔로잉 관계 있는지 확인
    public Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId) {
        return followRepository.findByFollowerIdAndFollowingId(followerId, followingId);
    }
    
    // 팔로우 삭제
    public void delete(Follow follow) {
        followRepository.delete(follow);
    }
    
    // 팔로우 저장
    public void save(Follow follow) {
        followRepository.save(follow);
    }

}
