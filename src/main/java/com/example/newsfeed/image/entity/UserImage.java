package com.example.newsfeed.image.entity;

import com.example.newsfeed.image.dto.request.ImageRequestDto;
import com.example.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users_image")
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "image_type", nullable = false)
    private String imageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserImage(String imageUrl, String imageType, User user) {
        this.imageUrl = imageUrl;
        this.imageType = imageType;
        this.user = user;
    }

    public static UserImage toEntity(User user, ImageRequestDto images) {
        return new UserImage(images.getImageUrl(), images.getImageType(), user);
    }
}
