package com.example.newsfeed.image.entity;

import com.example.newsfeed.board.entity.Board;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "board_images")
public class BoardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "image_type", nullable = false)
    private String imageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public BoardImage(String imageUrl, String imageType, Board board) {
        this.imageUrl = imageUrl;
        this.imageType = imageType;
        this.board = board;
    }
}
