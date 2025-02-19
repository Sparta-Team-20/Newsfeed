package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.dto.request.CommentSaveRequestDto;
import com.example.newsfeed.comment.dto.request.CommentUpdateRequestDto;
import com.example.newsfeed.comment.dto.response.CommentResponseDto;
import com.example.newsfeed.comment.dto.response.CommentSaveResponseDto;
import com.example.newsfeed.comment.dto.response.CommentUpdateResponseDto;
import com.example.newsfeed.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/boards/{boardId}/comments")
    public ResponseEntity<CommentSaveResponseDto> save(@PathVariable Long boardId,
                                                       @RequestBody @Valid CommentSaveRequestDto dto,
                                                       HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("LOGIN_USER");
        return new ResponseEntity<>(commentService.save(boardId, dto, userId), HttpStatus.CREATED);
    }

    @GetMapping("/boards/{boardId}/comments/{id}")
    public ResponseEntity<CommentResponseDto> findOne(@PathVariable Long boardId, @PathVariable Long id) {
        return ResponseEntity.ok(commentService.findOne(boardId, id));
    }

    @GetMapping("/boards/{boardId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findAll(@PathVariable Long boardId) {
        return ResponseEntity.ok(commentService.findAll(boardId));
    }

    @PatchMapping("/comments/{id}")
    public ResponseEntity<CommentUpdateResponseDto> update(@PathVariable Long id,
                                                           @RequestBody @Valid CommentUpdateRequestDto dto,
                                                           HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("LOGIN_USER");

        return ResponseEntity.ok(commentService.update(id, dto, userId));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("LOGIN_USER");
        commentService.delete(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}