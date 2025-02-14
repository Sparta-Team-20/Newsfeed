package com.example.newsfeed.user.entity;

import com.example.newsfeed.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @Column(name = "is_delete")
    private int isDeleted;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }


}
