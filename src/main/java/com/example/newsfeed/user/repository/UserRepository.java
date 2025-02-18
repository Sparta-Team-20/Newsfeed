package com.example.newsfeed.user.repository;

import com.example.newsfeed.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.isDeleted = false")
    @EntityGraph(attributePaths = {"images"})
    List<User> findAllBy();

    @EntityGraph(attributePaths = {
            "followers.follower",
            "followings.following",
            "images"
    })
    Optional<User> findUsersById(Long id);

    Optional<User> findByEmail(String email);
}
