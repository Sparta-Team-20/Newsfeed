package com.example.newsfeed.comment.repository;

import com.example.newsfeed.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

    default Comment findCommentById(Long id) {
        return findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 댓글을 찾을 수 없습니다. ID = " + id)
        );
    }

    /**
     * 댓글(내용)을 업데이트하는 메서드입니다.
     * 주어진 ID를 가진 댓글을 새로 전달 받은 내용으로 변경합니다.
     *
     * @param id 댓글의 고유 ID
     * @param content 업데이트할 새로운 댓글
     * @throws ResponseStatusException 댓글을 찾을 수 없는 경우 발생
    */
    @Modifying
    @Query("UPDATE Comment c SET c.content = :content WHERE c.id = :id")
    void updateContent(Long id, String content);

}
