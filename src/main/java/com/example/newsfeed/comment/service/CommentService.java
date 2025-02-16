package com.example.newsfeed.comment.service;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.comment.dto.*;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
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

    @Transactional
    public CommentResponseDto findCommentById(Long boardId, Long id) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 게시글이 존재하지 않습니다.")
        );

        Comment comment = commentRepository.findByIdAndBoardId(board.getId(), id);

        return new CommentResponseDto(comment.getId(),
                                      comment.getUser().getId(),
                                      comment.getBoard().getId(),
                                      comment.getContent(),
                                      String.valueOf(comment.getCreatedAt()),
                                      String.valueOf(comment.getCreatedAt())
        );

    }

    @Transactional
    public List<CommentResponseDto> findAllComments(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 게시글이 존재하지 않습니다.")
        );
        List<Comment> comments = commentRepository.findAllByBoardId(board.getId());
        List<CommentResponseDto> dtoList = new ArrayList<>();

        for (Comment comment : comments) {
            dtoList.add(new CommentResponseDto(comment.getId(),
                                               comment.getUser().getId(),
                                               comment.getBoard().getId(),
                                               comment.getContent(),
                                               String.valueOf(comment.getCreatedAt()),
                                               String.valueOf(comment.getCreatedAt())
            ));
        }

        return dtoList;
    }

    public CommentUpdateResponseDto updateComment(Long id, CommentUpdateRequestDto dto) {
        Comment comment = commentRepository.findCommentById(id);
        commentRepository.updateContent(comment.getId(), dto.getContent());

        return new CommentUpdateResponseDto(comment.getId(),
                                            comment.getUser().getId(),
                                            comment.getBoard().getId(),
                                            comment.getContent(),
                                            String.valueOf(comment.getCreatedAt()),
                                            String.valueOf(comment.getCreatedAt())
        );
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findCommentById(id);
        commentRepository.deleteById(comment.getId());
    }

}
