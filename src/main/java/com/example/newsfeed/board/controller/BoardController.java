package com.example.newsfeed.board.controller;

import com.example.newsfeed.board.dto.request.BoardSaveRequestDto;
import com.example.newsfeed.board.dto.request.BoardUpdateRequestDto;
import com.example.newsfeed.board.dto.response.BoardResponseDto;
import com.example.newsfeed.board.dto.response.BoardPageResponseDto;
import com.example.newsfeed.board.dto.response.BoardSaveResponseDto;
import com.example.newsfeed.board.dto.response.BoardUpdateResponseDto;
import com.example.newsfeed.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BoardController {

    private final BoardService boardService;

    // 게시물 생성
    @PostMapping("/boards")
    public ResponseEntity<BoardSaveResponseDto> save(HttpServletRequest request, @RequestBody BoardSaveRequestDto dto
    ) {
        Long userId = (Long) request.getAttribute("LOGIN_USER");
        return ResponseEntity.ok(boardService.save(userId, dto));
    }

    // 게시물 단건 조회
    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.findOne(id));
    }

    // 게시물 수정
    @PutMapping("/boards/{id}")
    public ResponseEntity<BoardUpdateResponseDto> update(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody BoardUpdateRequestDto dto
    ) {
        Long userId = (Long) request.getAttribute("LOGIN_USER");
        return ResponseEntity.ok(boardService.update(id, userId, dto));
    }

    // 게시물 삭제
    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> delete(
            HttpServletRequest request,
            @PathVariable Long id
    ) {
        Long userId = (Long) request.getAttribute("LOGIN_USER");
        boardService.delete(id, userId);
        return ResponseEntity.ok().build();
    }

    // 게시물 페이지 API
    @GetMapping("/boards/page")
    public ResponseEntity<Page<BoardPageResponseDto>> findAllPage(@PageableDefault(size = 10) Pageable pageable) {
        Page<BoardPageResponseDto> result = boardService.findAllPage(pageable);
        return ResponseEntity.ok(result);
    }
}

