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
import com.example.newsfeed.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserService userService;
    
    
    // 댓글 저장
    public CommentSaveResponseDto save(Long boardId, CommentSaveRequestDto dto, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_BOARD)
                );

        User findUser = userService.findById(userId);

        Comment comment = new Comment(dto.getContents(), board, findUser);
        Comment savedComment = commentRepository.save(comment);
        return CommentSaveResponseDto.of(savedComment);
    }
    
    // 댓글 단건 조회
    public CommentResponseDto findOne(Long boardId, Long id) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_BOARD)
                );

        Comment comment = commentRepository.findByIdAndBoardId(id, board.getId());
        return CommentResponseDto.of(comment);
    }
    
    // 게시물에 포함된 댓글 전체 조회
    public List<CommentResponseDto> findAll(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_BOARD)
                );

        List<Comment> comments = commentRepository.findAllByBoardId(board.getId());
        return comments.stream().map(CommentResponseDto::of).toList();
    }
    
    // 댓글 수정
    @Transactional
    public CommentUpdateResponseDto update(Long id, CommentUpdateRequestDto dto, Long userId) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_COMMENT)
        );

        User findUser = userService.findById(userId);

        if (!comment.getUser().equals(findUser)) {
            throw new CustomExceptionHandler(ErrorCode.INVALID_USER_UPDATE_COMMENT);
        }

        comment.update(dto.getContents());
        return CommentUpdateResponseDto.of(comment);
    }
    
    // 댓글 삭제
    @Transactional
    public void delete(Long id, Long userId) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_COMMENT)
        );

        User findUser = userService.findById(userId);

        if (!comment.getUser().equals(findUser)) {
            throw new CustomExceptionHandler(ErrorCode.INVALID_USER_DELETE_COMMENT);
        }

        comment.delete();
    }

}
