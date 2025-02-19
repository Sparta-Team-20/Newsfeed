package com.example.newsfeed.board.service;

import com.example.newsfeed.board.repository.BoardRepository;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardDeleteService {

    private final BoardRepository boardRepository;

    @Transactional
    public void delete(User user) {
        boardRepository.softDeleteByUserId(user.getId());
    }

}
