package com.example.newsfeed.follow.repository;

import com.example.newsfeed.follow.dto.FollowCountDto;
import com.example.newsfeed.follow.entity.Follow;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("SELECT new com.example.newsfeed.follow.dto.FollowCountDto(f.follower.id, COUNT(f)) " +
            "FROM Follow f WHERE f.follower.id IN :userIds GROUP BY f.follower.id")
    List<FollowCountDto> countFollowersByUserIds(@Param("userIds") List<Long> userIds);

    @Query("SELECT new com.example.newsfeed.follow.dto.FollowCountDto(f.following.id, COUNT(f)) " +
            "FROM Follow f WHERE f.following.id IN :userIds GROUP BY f.following.id")
    List<FollowCountDto> countFollowingsByUserIds(@Param("userIds") List<Long> userIds);

    List<Follow> findByFollowerId(Long userId);

    @EntityGraph(attributePaths = {"follower"})
    @Query("SELECT f FROM Follow f WHERE f.following.id = :userId")
    List<Follow> findByFollowingId(Long userId);

    Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
