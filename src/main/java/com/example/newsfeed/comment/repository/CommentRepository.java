package com.example.newsfeed.comment.repository;

import com.example.newsfeed.comment.dto.CommentCountDto;
import com.example.newsfeed.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByIdAndBoardId(Long boardId, Long id);

    /**
     * 특정한 게시글의 모든 댓글을 리스트 형식으로 반환하는 메서드입니다.
     * 수정일자의 내림차순으로 정렬하여 결과(모든 댓글)를 반환합니다.
     *
     * @param boardId 게시글의 고유 ID
     */
    @Query("SELECT c FROM Comment c WHERE c.board.id = :boardId ORDER BY c.modifiedAt DESC")
    List<Comment> findAllByBoardId(Long boardId);
    List<CommentCountDto> countByBoardIds(List<Long> boardIds);

}
