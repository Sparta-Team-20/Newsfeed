package com.example.newsfeed.user.entity;

import com.example.newsfeed.common.entity.BaseEntity;
import com.example.newsfeed.user.dto.request.UserSaveRequestDto;
import com.example.newsfeed.user.dto.request.UserUpdateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @Column(name = "is_deleted")
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
}
