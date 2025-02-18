package com.example.newsfeed.comment.service;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.board.repository.BoardRepository;
import com.example.newsfeed.comment.dto.request.CommentSaveRequestDto;
import com.example.newsfeed.comment.dto.request.CommentUpdateRequestDto;
import com.example.newsfeed.comment.dto.response.CommentResponseDto;
import com.example.newsfeed.comment.dto.response.CommentSaveResponseDto;
import com.example.newsfeed.comment.dto.response.CommentUpdateResponseDto;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.common.exception.CustomExceptionHandler;
import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentSaveResponseDto save(Long boardId, CommentSaveRequestDto dto, User loginUser) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new CustomExceptionHandler(ErrorCode.NOT_FOUND_BOARD)
        );

        Comment comment = new Comment(dto.getContent(), board, loginUser);
        Comment savedComment = commentRepository.save(comment);

        return new CommentSaveResponseDto(savedComment.getId(),
                                          savedComment.getUser().getId(),
                                          savedComment.getBoard().getId(),
                                          savedComment.getContent(),
                                          savedComment.getCreatedAt(),
                                          savedComment.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findOne(Long boardId, Long id) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new CustomExceptionHandler(ErrorCode.NOT_FOUND_BOARD)
        );

        Comment comment = commentRepository.findByIdAndBoardId(board.getId(), id);

        return new CommentResponseDto(comment.getId(),
                                      comment.getUser().getId(),
                                      comment.getBoard().getId(),
                                      comment.getContent(),
                                      comment.getCreatedAt(),
                                      comment.getCreatedAt()
        );

    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new CustomExceptionHandler(ErrorCode.NOT_FOUND_BOARD)
        );

        List<Comment> comments = commentRepository.findAllByBoardId(board.getId());
        return comments.stream()
                .map(comment -> new CommentResponseDto(comment.getId(),
                                               comment.getUser().getId(),
                                               comment.getBoard().getId(),
                                               comment.getContent(),
                                               comment.getCreatedAt(),
                                               comment.getCreatedAt())
                )
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentUpdateResponseDto update(Long id, CommentUpdateRequestDto dto ,User loginUser) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_COMMENT)
        );

        if (!comment.getUser().equals(loginUser)) {
            throw new CustomExceptionHandler(ErrorCode.INVALID_USER_UPDATE_COMMENT);
        }

        comment.updateContent(dto.getContent());

        return new CommentUpdateResponseDto(comment.getId(),
                                            comment.getUser().getId(),
                                            comment.getBoard().getId(),
                                            comment.getContent(),
                                            comment.getCreatedAt(),
                                            comment.getCreatedAt()
        );
    }

    @Transactional
    public void delete(Long id, User loginUser) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_COMMENT)
        );

        if (!comment.getUser().equals(loginUser)) {
            throw new CustomExceptionHandler(ErrorCode.INVALID_USER_DELETE_COMMENT);
        }

        commentRepository.deleteById(comment.getId());
    }

}
