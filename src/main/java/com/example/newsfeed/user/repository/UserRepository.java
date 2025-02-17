package com.example.newsfeed.user.repository;

import com.example.newsfeed.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"images"})
    List<User> findAllBy();

    @EntityGraph(attributePaths = {
            "followers.follower",
            "followings.following",
            "images"
    })
    Optional<User> findById(Long id);
}
