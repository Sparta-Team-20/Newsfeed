package com.example.newsfeed.comment.service;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentDeleteService {

    private final CommentRepository commentRepository;
    
    // 유저와 연관된 댓글 삭제
    @Transactional
    public void delete(User user) {
        commentRepository.softDeleteByUserId(user.getId());
    }
    
    // 게시물과 연관된 댓글 삭제
    @Transactional
    public void delete(Board board) {
        commentRepository.softDeleteByBoardId(board.getId());
    }

}
