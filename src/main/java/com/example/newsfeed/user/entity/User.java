package com.example.newsfeed.user.entity;

import com.example.newsfeed.common.entity.BaseEntity;
import com.example.newsfeed.follow.entity.Follow;
import com.example.newsfeed.image.entity.UserImage;
import com.example.newsfeed.user.dto.request.UserSaveRequestDto;
import com.example.newsfeed.user.dto.request.UserUpdateRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> followers = new HashSet<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> followings = new HashSet<>();

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

    public void update(UserUpdateRequestDto request, String encodedPassword) {
        this.password = encodedPassword;
        this.name = request.getName();
    }

    public void delete() {
        this.isDeleted = true;
    }

    public UserImage getFirstImage() {
        return this.getImages().isEmpty() ? new UserImage("none", "png", this) : this.getImages().get(0);
    }

    public User(Long id) {
        this.id = id;
    }
}
