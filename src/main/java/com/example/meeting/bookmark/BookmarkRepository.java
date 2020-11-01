package com.example.meeting.bookmark;

import com.example.meeting.board.Board;
import com.example.meeting.match.Matched;
import com.example.meeting.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("SELECT m FROM Bookmark m WHERE m.user = ?1 AND m.board = ?2")
    public Bookmark deleteGet(User user, Board board);
    public List<Bookmark> findByUser(User user);
    
   

}
