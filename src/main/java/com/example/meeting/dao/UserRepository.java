package com.example.meeting.dao;


import com.example.meeting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT b.idx FROM user b WHERE b.email like ?1")
    public Long getFromEmail(String email);
    
    public User findByIdx(Long idx);

    public User findByEmail(String email);
    public User findByNickName(String nickName);

    @Transactional
    @Modifying
    @Query(value="UPDATE user u SET u.point = 10 WHERE u.idx >=0", nativeQuery=false)
    public void changePoint();

}
