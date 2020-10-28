package com.example.meeting.user;


import com.example.meeting.board.Board;
import com.example.meeting.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT b.idx FROM user b WHERE b.email like ?1")
    public Long getFromEmail(String email);
    
    public User findByIdx(Long idx);

    public User findByEmail(String email);
    public User findByNickName(String nickName);

}
