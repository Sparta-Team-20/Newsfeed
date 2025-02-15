package com.example.newsfeed.board.controller;

import com.example.newsfeed.board.dto.BoardResponseDto;
import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 생성
    // @PostMapping("/api/v1/boards")
    // public ResponseEntity<BoardResponseDto>

    // 조회

    // 수정

    // 삭제

}
