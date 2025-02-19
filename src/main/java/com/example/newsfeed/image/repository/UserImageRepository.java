package com.example.newsfeed.image.repository;

import com.example.newsfeed.image.entity.UserImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Long> {


    @Query("SELECT ui FROM UserImage ui JOIN FETCH ui.user WHERE ui.createdAt = " +
            "(SELECT MAX(u2.createdAt) FROM UserImage u2 WHERE u2.user = ui.user) " +
            "AND ui.user.id IN :userIds " +
            "ORDER BY ui.createdAt DESC")
    List<UserImage> findLatestImagesPerUser(List<Long> userIds);


    List<UserImage> findAllByUserId(Long id);
}
