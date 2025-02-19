package com.example.newsfeed.board.controller;

import com.example.newsfeed.board.dto.request.BoardSaveRequestDto;
import com.example.newsfeed.board.dto.request.BoardUpdateRequestDto;
import com.example.newsfeed.board.dto.response.BoardFindAllResponseDto;
import com.example.newsfeed.board.dto.response.BoardFindOneResponseDto;
import com.example.newsfeed.board.dto.response.BoardPageResponseDto;
import com.example.newsfeed.board.dto.response.BoardSaveResponseDto;
import com.example.newsfeed.board.dto.response.BoardUpdateResponseDto;
import com.example.newsfeed.board.service.BoardService;
import com.example.newsfeed.common.consts.Const;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
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
    public ResponseEntity<List<BoardFindAllResponseDto>> findAll() {
        return ResponseEntity.ok(boardService.findAll());
    }

    // 게시물 단건 조회
    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardFindOneResponseDto> findOne(@PathVariable Long id) {
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
        boardService.delete(id, userId);
        return ResponseEntity.ok().build();
    }

    // 게시물 페이지 API
    @GetMapping("/boards/page")
    public ResponseEntity<Page<BoardPageResponseDto>> findAllPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BoardPageResponseDto> result = boardService.findAllPage(page, size);
        return ResponseEntity.ok(result);
    }
}
