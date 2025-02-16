package com.example.newsfeed.board.controller;

import com.example.newsfeed.board.consts.Const;
import com.example.newsfeed.board.dto.*;
import com.example.newsfeed.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시물 생성
    @PostMapping("/boards")
    public ResponseEntity<BoardSaveResponseDto> save(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody BoardSaveRequestDto dto
    ) {
        return ResponseEntity.ok(boardService.save(userId, dto));
    }

    // 게시물 전체 조회
    @GetMapping("/boards")
    public ResponseEntity<List<BoardResponseDto>> findAll() {
        return ResponseEntity.ok(boardService.findAll());
    }

    // 게시물 단건 조회
    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.findOne(id));
    }

    // 게시물 수정
    @PutMapping("/boards/{id}")
    public ResponseEntity<BoardUpdateResponseDto> update(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable Long id,
            @RequestBody BoardUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(boardService.update(id, userId, dto));
    }

    // 게시물 삭제
    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable Long id
    ) {
        boardService.deleteById(id, userId);
        return ResponseEntity.ok().build();
    }

    // 게시물 페이지 API
    @GetMapping("/boards/{page}")
    public ResponseEntity<Page<BoardPageResponseDto>> findAllPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BoardPageResponseDto> result = boardService.findAllPage(page, size);
        return ResponseEntity.ok(result);
    }
}

