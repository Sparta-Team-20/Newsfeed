package com.example.newsfeed.image.entity;

import static com.example.newsfeed.common.consts.Const.IMAGE_PATTERN;
import static com.example.newsfeed.common.consts.Const.IMAGE_REQUIREMENT;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.common.utils.FileUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "boards_image")
public class BoardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "image_type", nullable = false)
    @Pattern(regexp = IMAGE_PATTERN, message = IMAGE_REQUIREMENT)
    private String imageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public BoardImage(String imageUrl, String imageType, Board board) {
        this.imageUrl = imageUrl;
        this.imageType = imageType;
        this.board = board;
    }

    public static BoardImage toEntity(Board board, String images) {
        String[] splitImages = FileUtils.splitFileName(images);
        return new BoardImage(splitImages[0], splitImages[1], board);
    }
}
