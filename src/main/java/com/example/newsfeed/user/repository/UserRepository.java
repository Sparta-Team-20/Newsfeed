package com.example.newsfeed.user.repository;

import com.example.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByFollowerAndFollowing(Long userId, Long id);

    List<FollowCount> countByFollowers(List<Long> userIds);
}
