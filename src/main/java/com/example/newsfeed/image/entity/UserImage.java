package com.example.newsfeed.image.entity;

import static com.example.newsfeed.common.consts.Const.IMAGE_PATTERN;
import static com.example.newsfeed.common.consts.Const.IMAGE_REQUIREMENT;

import com.example.newsfeed.common.entity.BaseEntity;
import com.example.newsfeed.common.utils.FileUtils;
import com.example.newsfeed.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users_image")
public class UserImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "image_type", nullable = false)
    @Pattern(regexp = IMAGE_PATTERN, message = IMAGE_REQUIREMENT)
    private String imageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserImage(String imageUrl, String imageType, User user) {
        this.imageUrl = imageUrl;
        this.imageType = imageType;
        this.user = user;
    }

    public static UserImage toEntity(User user, String images) {
        String[] splitImage = FileUtils.splitFileName(images);
        return new UserImage(splitImage[0], splitImage[1], user);
    }
}
