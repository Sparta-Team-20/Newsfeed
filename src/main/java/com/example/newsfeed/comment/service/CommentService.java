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
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        Comment comment = new Comment(dto.getContent(), board, loginUser);
        Comment savedComment = commentRepository.save(comment);

        return new CommentSaveResponseDto(savedComment.getId(),
                                          savedComment.getUser().getId(),
                                          savedComment.getBoard().getId(),
                                          savedComment.getContent(),
                                          String.valueOf(savedComment.getCreatedAt()),
                                          String.valueOf(savedComment.getCreatedAt())
        );
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findOne(Long boardId, Long id) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
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

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        List<Comment> comments = commentRepository.findAllByBoardId(board.getId());
        return comments.stream()
                .map(comment -> new CommentResponseDto(comment.getId(),
                                               comment.getUser().getId(),
                                               comment.getBoard().getId(),
                                               comment.getContent(),
                                               String.valueOf(comment.getCreatedAt()),
                                               String.valueOf(comment.getCreatedAt()))
                )
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentUpdateResponseDto update(Long id, CommentUpdateRequestDto dto ,User loginUser) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 댓글을 찾을 수 없습니다. ID = " + id)
        );

        if (!comment.getUser().equals(loginUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "댓글 작성자만 수정할 수 있습니다.");
        }

        comment.updateContent(dto.getContent());

        return new CommentUpdateResponseDto(comment.getId(),
                                            comment.getUser().getId(),
                                            comment.getBoard().getId(),
                                            comment.getContent(),
                                            String.valueOf(comment.getCreatedAt()),
                                            String.valueOf(comment.getCreatedAt())
        );
    }

    @Transactional
    public void delete(Long id, User loginUser) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 댓글을 찾을 수 없습니다. ID = " + id)
        );

        if (!comment.getUser().equals(loginUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "댓글 작성자만 삭제할 수 있습니다.");
        }

        commentRepository.deleteById(comment.getId());
    }

}
