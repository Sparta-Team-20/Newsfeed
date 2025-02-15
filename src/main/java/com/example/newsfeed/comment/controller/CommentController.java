package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.dto.*;
import com.example.newsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/boards/{boardId}/comments")
    public ResponseEntity<CommentSaveResponseDto> saveComment(
            @PathVariable Long userId,
            @PathVariable Long boardId,
            @RequestBody CommentSaveRequestDto dto
    ) {
        return new ResponseEntity<>(commentService.saveComment(boardId, dto), HttpStatus.CREATED);
    }

    @GetMapping("/boards/{boardId}/comments")
    public ResponseEntity<CommentResponseDto> findCommentById(
            @PathVariable Long userId,
            @PathVariable Long boardId,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(commentService.findCommentById(boardId, id));
    }

    @GetMapping("/boards/{boardId}/comments")
    public ResponseEntity<CommentResponseDto> findAllComments(@PathVariable Long boardId) {
        return ResponseEntity.ok(commentService.findAllComments(boardId));
    }

    @PatchMapping("/comments/{id}")
    public ResponseEntity<CommentUpdateResponseDto> updateComment(
            @PathVariable Long id,
            @RequestBody CommentUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(commentService.updateComment(id, dto));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
