package com.example.meeting.board;


import com.example.meeting.board.Board;
import com.example.meeting.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BoardRepository extends JpaRepository<Board, Long> {
    public List<Board> findByAgeLessThanEqualAndAgeGreaterThanEqual(int age1, int age2);
//    public User findByEmail(String email);
}
