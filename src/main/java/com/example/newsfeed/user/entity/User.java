package com.example.newsfeed.user.entity;

import com.example.newsfeed.common.entity.BaseEntity;
import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.image.entity.UserImage;
import com.example.newsfeed.user.dto.request.UserSaveRequestDto;
import com.example.newsfeed.user.dto.request.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserImage> images = new ArrayList<>();

    @Column(name = "is_delete")
    private boolean isDeleted;

    private User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.isDeleted = false;
    }


    public static User toEntity(UserSaveRequestDto request, String encodedPassword) {
        return new User(request.getEmail(), encodedPassword, request.getName());
    }

    public void update(UserUpdateRequestDto request, String encodedPassword, List<UserImage> images) {
        this.password = encodedPassword;
        this.name = request.getName();
        this.images = images;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public Long getFollowersSize() {
        return (long) this.followers.size();
    }
}
