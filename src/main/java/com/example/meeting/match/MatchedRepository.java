package com.example.meeting.match;

import com.example.meeting.board.Board;
import com.example.meeting.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface MatchedRepository extends JpaRepository<Matched, Long> {

    @Query("SELECT m FROM Matched m WHERE m.idx = ?1")
    Matched findIdx(Long idx);

    @Query("SELECT m FROM Matched m WHERE m.sender.idx = ?1 AND m.is_matched = false Order By m.board.date")
    List<Matched> findSend(Long senderId);

    @Query("SELECT m FROM Matched m WHERE m.board.user.idx = ?1 ORDER BY m.board.idx")
    List<Matched> findMaker(Long makerId);

    @Query("SELECT m FROM Matched m WHERE (m.board.user.idx = ?1 OR m.sender.idx = ?1)  AND m.is_matched = true ORDER BY m.board.idx")
    List<Matched> findMatch(Long makerId);

    @Query("SELECT m FROM Matched m WHERE m.board = ?1")
    List<Matched> findBoard(Board board);

    @Query("SELECT m FROM Matched m WHERE m.sender.idx = ?1 AND m.board.idx = ?2")
    List<Matched> findMatched(Long senderId,  Long boardId);

    @Query("SELECT m FROM Matched m WHERE m.board.idx = ?1 AND m.is_matched = true")
    Matched findForBoard(Long boardId);

}
