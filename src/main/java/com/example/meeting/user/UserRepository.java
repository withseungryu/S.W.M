package com.example.meeting.user;


import com.example.meeting.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
    public User findByNickName(String nickName);
    public User findByIdx(Long idx);
}
