package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.dto.request.CommentSaveRequestDto;
import com.example.newsfeed.comment.dto.request.CommentUpdateRequestDto;
import com.example.newsfeed.comment.dto.response.CommentResponseDto;
import com.example.newsfeed.comment.dto.response.CommentSaveResponseDto;
import com.example.newsfeed.comment.dto.response.CommentUpdateResponseDto;
import com.example.newsfeed.comment.service.CommentService;
import com.example.newsfeed.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/boards/{boardId}/comments")
    public ResponseEntity<CommentSaveResponseDto> save(
            @PathVariable Long boardId,
            @RequestBody @Valid CommentSaveRequestDto dto,
            @SessionAttribute(name = "loginUser", required = false) User loginUser
    ) {
        if (loginUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(commentService.save(boardId, dto, loginUser), HttpStatus.CREATED);
    }

    @GetMapping("/boards/{boardId}/comments/{id}")
    public ResponseEntity<CommentResponseDto> findOne(
            @PathVariable Long boardId,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(commentService.findOne(boardId, id));
    }

    @GetMapping("/boards/{boardId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findAll(@PathVariable Long boardId) {
        return ResponseEntity.ok(commentService.findAll(boardId));
    }

    @PatchMapping("/comments/{id}")
    public ResponseEntity<CommentUpdateResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid CommentUpdateRequestDto dto,
            @SessionAttribute(name = "loginUser", required = false) User loginUser
    ) {
        if (loginUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(commentService.update(id, dto, loginUser));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @SessionAttribute(name = "loginUser", required = false) User loginUser
    ) {
        if (loginUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        commentService.delete(id, loginUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
