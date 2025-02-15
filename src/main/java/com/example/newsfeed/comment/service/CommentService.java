package com.example.newsfeed.comment.service;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.comment.dto.*;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public CommentSaveResponseDto saveComment(Long boardId, CommentSaveRequestDto dto) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 게시글이 존재하지 않습니다.")
        );

        Comment comment = new Comment(dto.getContent(), board, board.getUser());
        Comment savedComment = commentRepository.save(comment);

        return new CommentSaveResponseDto(savedComment.getId(),
                                          savedComment.getUser().getId(),
                                          savedComment.getBoard().getId(),
                                          savedComment.getContent(),
                                          String.valueOf(savedComment.getCreatedAt()),
                                          String.valueOf(savedComment.getCreatedAt())
        );
    }


    public CommentResponseDto findCommentById(Long boardId, Long id) {

    }

    public CommentResponseDto findAllComments(Long boardId) {

    }

    public CommentUpdateResponseDto updateComment(Long id, CommentUpdateRequestDto dto) {

    }

    public void deleteComment(Long id) {

    }

}
