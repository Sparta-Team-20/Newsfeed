package com.example.newsfeed.board.service;

import com.example.newsfeed.board.dto.*;
import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.board.repository.BoardRepository;
import com.example.newsfeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 게시물 생성
    @Transactional
    public BoardSaveResponseDto save(Long userId, BoardSaveRequestDto dto) {
        User user = User.fromUserId(userId);
        Board board = new Board(user, dto.getTitle(), dto.getContents());
        boardRepository.save(board);
        return new BoardSaveResponseDto(
                board.getId(),
                user.getId(),
                board.getTitle(),
                board.getContents(),
                board.getCreatedAt(),
                board.getModifiedAt()
        );
    }

    // 게시물 다건 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto> findAll() {
        return boardRepository.findAll().stream()
                .map(board -> new BoardResponseDto(
                        board.getId(),
                        board.getUser().getId(),
                        board.getTitle(),
                        board.getContents(),
                        board.getCreatedAt(),
                        board.getModifiedAt()))
                .collect(Collectors.toList());
    }

    // 게시물 단건 조회
    @Transactional(readOnly = true)
    public BoardResponseDto findOne(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return new BoardResponseDto(
                board.getId(),
                board.getUser().getId(),
                board.getTitle(),
                board.getContents(),
                board.getCreatedAt(),
                board.getModifiedAt());
    }

    // 게시물 수정
    @Transactional
    public BoardUpdateResponseDto update(Long boardId, Long userId, BoardUpdateRequestDto dto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        if(!userId.equals(board.getUser().getId())) {
            throw new IllegalArgumentException("본인이 작성한 스케줄만 수정할 수 있습니다.");
        }
        board.update(dto.getTitle(), dto.getContents());
        return new BoardUpdateResponseDto(
                board.getId(),
                board.getUser().getId(),
                board.getTitle(),
                board.getContents(),
                board.getCreatedAt(),
                board.getModifiedAt());
    }

    // 게시물 삭제
    @Transactional
    public void deleteById(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                        .orElseThrow(()-> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));
        if(!userId.equals(board.getUser().getId())) {
            throw new IllegalArgumentException("본인이 작성한 스케줄만 삭제할 수 있습니다.");
        }
        boardRepository.delete(board);
    }

    @Transactional(readOnly = true)
    public Page<BoardResponseDto> findAllPage(int page, int size) {
        // 클라이언트에서 1부터 전달된 페이지 번호를 0 기반으로 조정
        int adjustedPage = (page > 0) ? page -1 : 0;
        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("modifiedAt").descending());
        // 1. Schedule Page 조회
        Page<Board> boardPage = boardRepository.findAll(pageable);
        // 2. 일정 ID 리스트 추출
        List<Long> boardIds = boardPage.stream()
                .map(Board::getId)
                .collect(Collectors.toList());
        // 3. 별도 쿼리로 댓글 수 조회
        List<CommentCountDto> countResults = commentRepository.countByBoardIds(boardIds);
        Map<Long, Long> commentCountMap = countResults.stream()
                .collect(Collectors.toMap(CommentCountDto::getBoardId, CommentCountDto::getCount));
        // 4. 각 Board를 BoardPageResponseDto로 반환 (댓글 수는 Long을 int로 변환)
        return boardPage.map(board -> new BoardPageResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContents(),
                commentCountMap.getOrDefault(board.getId(), 0L).intValue(),
                board.getCreatedAt(),
                board.getModifiedAt(),
                board.getUser().getUserName()
        ));

    }
}

