package com.example.newsfeed.board.repository;

import com.example.newsfeed.board.entity.Board;
import com.example.newsfeed.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByUser(User user);

    @Modifying
    @Transactional
    @Query("UPDATE Board b SET b.isDeleted = true WHERE b.user.id = :id")
    int softDeleteByUserId(Long id);
}
