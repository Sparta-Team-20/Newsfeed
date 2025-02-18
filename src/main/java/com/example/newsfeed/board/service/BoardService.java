package com.example.newsfeed.board.service;

import com.example.newsfeed.board.dto.request.BoardSaveRequestDto;
import com.example.newsfeed.board.dto.request.BoardUpdateRequestDto;
import com.example.newsfeed.board.dto.response.BoardResponseDto;
import com.example.newsfeed.board.dto.response.BoardPageResponseDto;
import com.example.newsfeed.board.dto.response.BoardSaveResponseDto;
import com.example.newsfeed.board.dto.response.BoardUpdateResponseDto;
import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.board.repository.BoardRepository;
import com.example.newsfeed.comment.dto.CommentCountDto;
import com.example.newsfeed.comment.dto.response.CommentInfoResponseDto;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.common.exception.CustomExceptionHandler;
import com.example.newsfeed.common.exception.ErrorCode;
import com.example.newsfeed.image.dto.response.ImageResponseDto;
import com.example.newsfeed.image.entity.BoardImage;
import com.example.newsfeed.image.service.BoardImageService;
import com.example.newsfeed.user.entity.User;
import com.example.newsfeed.user.service.UserService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final BoardImageService boardImageService;

    // 게시물 생성
    @Transactional
    public BoardSaveResponseDto save(Long userId, BoardSaveRequestDto dto) {
        User user = userService.findById(userId);
        Board board = new Board(dto.getTitle(), dto.getContents(), user);
        List<BoardImage> images = dto.getImages().stream().map(image -> BoardImage.toEntity(board, image)).toList();

        boardRepository.save(board);
        boardImageService.save(images);
        List<ImageResponseDto> imageResponseList = images.stream()
                .map(ImageResponseDto::of).toList();

        return BoardSaveResponseDto.of(board, user, imageResponseList);
    }

    // 게시물 단건 조회
    @Transactional(readOnly = true)
    public BoardResponseDto findOne(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_BOARD));

        List<Comment> comments = commentRepository.findAllByBoardIdAndUser(board.getId());
        return BoardResponseDto.of(board, comments.stream().map(CommentInfoResponseDto::of).toList());
    }

    // 게시물 수정
    @Transactional
    public BoardUpdateResponseDto update(Long boardId, Long userId, BoardUpdateRequestDto dto) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_BOARD));

        User findUser = userService.findById(userId);

        if (!userId.equals(findBoard.getUser().getId())) {
            throw new CustomExceptionHandler(ErrorCode.INVALID_USER_UPDATE_BOARD);
        }

        List<BoardImage> boardImages = dto.getImages().stream().map(image -> BoardImage.toEntity(findBoard, image))
                .toList();

        findBoard.update(dto.getTitle(), dto.getContents(), boardImages);

        List<ImageResponseDto> imageResponseDtos = boardImages.stream()
                .map(ImageResponseDto::of)
                .toList();
        return BoardUpdateResponseDto.of(findBoard, findUser, imageResponseDtos);
    }

    // 게시물 삭제
    @Transactional
    public void delete(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomExceptionHandler(ErrorCode.NOT_FOUND_BOARD));
        if (!userId.equals(board.getUser().getId())) {
            throw new CustomExceptionHandler(ErrorCode.INVALID_USER_DELETE_BOARD);
        }
        boardRepository.delete(board);
    }

    @Transactional(readOnly = true)
    public Page<BoardPageResponseDto> findAllPage(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);

        List<Long> boardIds = boardPage.stream()
                .map(Board::getId)
                .collect(Collectors.toList());

        List<CommentCountDto> countResults = commentRepository.countByBoardIds(boardIds);
        Map<Long, Long> commentCountMap = countResults.stream()
                .collect(Collectors.toMap(CommentCountDto::getBoardId, CommentCountDto::getCount));

        return boardPage.map(board -> new BoardPageResponseDto(
                board.getId(),
                board.getTitle(),
                board.getContents(),
                commentCountMap.getOrDefault(board.getId(), 0L).intValue(),
                board.getCreatedAt(),
                board.getModifiedAt(),
                board.getUser().getName()
        ));
    }
}
