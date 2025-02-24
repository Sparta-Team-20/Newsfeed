package com.example.newsfeed.image.service;

import com.example.newsfeed.image.entity.BoardImage;
import com.example.newsfeed.image.repository.BoardImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardImageService {

    private final BoardImageRepository boardImageRepository;
    
    // 게시물 이미지 저장
    public void save(List<BoardImage> boardImage) {
        boardImageRepository.saveAll(boardImage);
    }

    public List<BoardImage> findAllByBoardIds(List<Long> boardIds) {
        return boardImageRepository.findAllByBoardIdIn(boardIds);
    }
}
