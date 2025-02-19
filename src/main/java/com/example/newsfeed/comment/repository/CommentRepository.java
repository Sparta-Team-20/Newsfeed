package com.example.newsfeed.comment.repository;

import com.example.newsfeed.comment.dto.CommentCountDto;
import com.example.newsfeed.comment.entity.Comment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByIdAndBoardId(Long id, Long BoardId);

    /**
     * 특정한 게시글의 모든 댓글을 리스트 형식으로 반환하는 메서드입니다.
     * 수정일자의 내림차순으로 정렬하여 결과(모든 댓글)를 반환합니다.
     *
     * @param boardId 게시글의 고유 ID
     */
    @Query("SELECT c FROM Comment c WHERE c.board.id = :boardId ORDER BY c.modifiedAt DESC")
    Page<Comment> findAllByBoardId(Long boardId, Pageable pageable);

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.board.id = :boardId")
    List<Comment> findAllByBoardIdAndUser(Long boardId);

    @Query("select new com.example.newsfeed.comment.dto.CommentCountDto(c.board.id, count(c)) " +
            "from Comment c " +
            "where c.board.id in :boardIds " +
            "group by c.board.id")
    List<CommentCountDto> countByBoardIds(List<Long> boardIds);

    List<Comment> findAllByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.isDeleted = true WHERE c.user.id = :id")
    int softDeleteByUserId(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.isDeleted = true WHERE c.board.id = :id")
    int softDeleteByBoardId(Long id);
}
