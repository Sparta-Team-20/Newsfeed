package com.example.newsfeed.board.repository;

import com.example.newsfeed.board.entity.Board;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b JOIN FETCH b.user LEFT JOIN FETCH b.images")
    List<Board> findAllWithUser();

}
