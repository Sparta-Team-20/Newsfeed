package com.example.newsfeed.image.entity;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.common.utils.FileUtils;
import com.example.newsfeed.image.dto.request.ImageRequestDto;
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
import org.springframework.web.multipart.MultipartFile;

import static com.example.newsfeed.common.consts.Const.IMAGES_PATTERN;
import static com.example.newsfeed.common.consts.Const.IMAGES_PATTERN__REQUIREMENT;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "boards_image")
public class BoardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false)
    @Pattern(regexp = IMAGES_PATTERN, message = IMAGES_PATTERN__REQUIREMENT)
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

    public static BoardImage toEntity(Board board, ImageRequestDto images) {
        return new BoardImage(images.getImageUrl(), images.getImageType(), board);
    }

    public static BoardImage toEntity(MultipartFile image, Board board) {
        String[] file = FileUtils.splitFileName(image);
        return new BoardImage(file[0], file[1], board);
    }
}
