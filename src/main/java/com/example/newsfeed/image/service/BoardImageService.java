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

    public void save(List<BoardImage> boardImage) {
        boardImageRepository.saveAll(boardImage);
    }
}
