package com.example.meeting.board;


import com.example.meeting.board.Board;
import com.example.meeting.bookmark.Bookmark;
import com.example.meeting.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BoardRepository extends JpaRepository<Board, Long> {


        public Board findByIdx(Long idx);



        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?1")
        public Page<Object[]> getfindAll(User user, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?2 WHERE b.location like ?1")
        public Page<Object[]> getList1(String location, User user, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?2 WHERE b.num_type like ?1 ")
        public Page<Object[]> getList2(String num_type, User user, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?3 WHERE b.age <= ?1 and b.age >= ?2")
        public Page<Object[]> getList3(int age1, int age2,User user, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?3 WHERE b.location like ?1 and b.num_type like ?2")
        public Page<Object[]> getList4(String location, String num_type, User user, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?4 WHERE b.location like ?1  and b.age <= ?2 and b.age >= ?3")
        public Page<Object[]> getList5(String location, int age1, int age2, User user, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?4 WHERE b.num_type like ?1 and b.age <= ?2 and b.age >= ?3")
        public Page<Object[]> getList6( String num_type, int age1, int age2, User user, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?5 WHERE b.location like ?1 and b.num_type like ?2 and b.age <= ?3 and b.age >= ?4")
        public Page<Object[]> getList7(String location, String num_type, int age1, int age2, User user, Pageable pageable);

        @Query("SELECT b.idx FROM Board b WHERE b.updatedDate = ( select Max(a.createdDate) from Board a )")
        public Integer test();



}
