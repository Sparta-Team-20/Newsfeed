package com.example.newsfeed.image.repository;

import com.example.newsfeed.image.entity.BoardImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
    List<BoardImage> findAllByBoardIdIn(List<Long> boardIds);
}
