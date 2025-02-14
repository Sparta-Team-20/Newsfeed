package com.example.newsfeed.board.entity;

import com.example.newsfeed.common.entity.BaseEntity;
import com.example.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contents;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Board(String title, String contents, String image,User user) {
        this.title = title;
        this.contents = contents;
        this.image = image;
        this.user = user;
    }

}
