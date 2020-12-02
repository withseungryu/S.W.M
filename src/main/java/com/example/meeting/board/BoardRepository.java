package com.example.meeting.board;


import com.example.meeting.board.Board;
import com.example.meeting.bookmark.Bookmark;
import com.example.meeting.match.Matched;
import com.example.meeting.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.sql.Timestamp;
import java.util.List;

@RepositoryRestResource
public interface BoardRepository extends JpaRepository<Board, Long> {


        public Board findByIdx(Long idx);

        @Query("SELECT b, m FROM Board b LEFT JOIN Matched m ON b.idx = m.board.idx WHERE b.user = ?1 ORDER BY b.idx")
        public List<Object[]> findUser(User maker);

        @Query("SELECT b, m, c FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?1 LEFT JOIN Matched c ON b.idx = c.board.idx AND c.is_matched = true WHERE b.date >= ?2 AND b.date <= ?3 AND c.is_matched is null")
        public Page<Object[]> getfindNullAll(User user, Timestamp date1, Timestamp date2,  Pageable pageable);

        @Query("SELECT b, m, c FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?1 LEFT JOIN Matched c ON b.idx = c.board.idx AND c.is_matched = true WHERE b.date >= ?2 AND b.date <= ?3 AND b.gender like ?4 AND c.is_matched is null")
        public Page<Object[]> getfindAll(User user, Timestamp date1, Timestamp date2, String gender, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?3 LEFT JOIN Matched c ON b.idx = c.board.idx AND c.is_matched = true WHERE b.location1 like ?1 AND b.location2 like ?2 AND b.date >= ?4 AND b.date <= ?5 AND b.gender like ?6 AND c.is_matched is null")
        public Page<Object[]> getList1(String location1, String location2, User user, Timestamp date1, Timestamp date2, String gender, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?2 LEFT JOIN Matched c ON b.idx = c.board.idx AND c.is_matched = true WHERE b.num_type like ?1 AND b.date >= ?3 AND b.date <= ?4 AND b.gender like ?5 AND c.is_matched is null")
        public Page<Object[]> getList2(String num_type, User user, Timestamp date1, Timestamp date2, String gender, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?3 LEFT JOIN Matched c ON b.idx = c.board.idx AND c.is_matched = true WHERE b.age <= ?1 and b.age >= ?2 AND b.date >= ?4 AND b.date <= ?5 AND b.gender like ?6 AND c.is_matched is null")
        public Page<Object[]> getList3(int age1, int age2,User user, Timestamp date1, Timestamp date2, String gender, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?4 LEFT JOIN Matched c ON b.idx = c.board.idx AND c.is_matched = true WHERE b.location1 like ?1 AND b.location2 like ?2 and b.num_type like ?3 AND b.date >= ?5 AND b.date <= ?6 AND b.gender like ?7 AND c.is_matched is null")
        public Page<Object[]> getList4(String location1, String location2, String num_type, User user, Timestamp date1, Timestamp date2, String gender, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?5 LEFT JOIN Matched c ON b.idx = c.board.idx AND c.is_matched = true WHERE b.location1 like ?1 AND b.location2 like ?2  and b.age <= ?3 and b.age >= ?4 AND b.date >= ?6 AND b.date <= ?7 AND b.gender like ?8 AND c.is_matched is null")
        public Page<Object[]> getList5(String location1, String location2, int age1, int age2, User user, Timestamp date1, Timestamp date2, String gender, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?4 LEFT JOIN Matched c ON b.idx = c.board.idx AND c.is_matched = true WHERE b.num_type like ?1 and b.age <= ?2 and b.age >= ?3 AND b.date >= ?5 AND b.date <= ?6 AND b.gender like ?7 AND c.is_matched is null")
        public Page<Object[]> getList6( String num_type, int age1, int age2, User user, Timestamp date1, Timestamp date2, String gender, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?6 LEFT JOIN Matched c ON b.idx = c.board.idx AND c.is_matched = true WHERE b.location1 like ?1 AND b.location2 like ?2 and b.num_type like ?3 and b.age <= ?4 and b.age >= ?5 AND b.date >= ?7 AND b.date <= ?8 AND b.gender like ?9 AND c.is_matched is null")
        public Page<Object[]> getList7(String location1, String location2, String num_type, int age1, int age2, User user, Timestamp date1, Timestamp date2, String gender, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?2 LEFT JOIN Matched c ON b.idx = c.board.idx AND c.is_matched = true WHERE b.location1 like ?1 AND b.date >= ?3 AND b.date <= ?4 AND b.gender like ?5 AND c.is_matched is null")
        public Page<Object[]> getList8(String location1, User user, Timestamp date1, Timestamp date2, String gender, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?4 LEFT JOIN Matched c ON b.idx = c.board.idx AND c.is_matched = true WHERE b.location1 like ?1and b.age <= ?2 and b.age >= ?3 AND b.date >= ?5 AND b.date <= ?6 AND b.gender like ?7 AND c.is_matched is null")
        public Page<Object[]> getList9(String location1, int age1, int age2, User user, Timestamp date1, Timestamp date2, String gender, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?3 LEFT JOIN Matched c ON b.idx = c.board.idx AND c.is_matched = true WHERE b.location1 like ?1  and b.num_type like ?2")
        public Page<Object[]> getList10(String location1, String num_type, User user, Timestamp date1, Timestamp date2, String gender, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?5 LEFT JOIN Matched c ON b.idx = c.board.idx AND c.is_matched = true WHERE b.location1 like ?1 AND  b.num_type like ?2 and b.age <= ?3 and b.age >= ?4 AND b.date >= ?6 AND b.date <= ?7 AND b.gender like ?8 AND c.is_matched is null")
        public Page<Object[]> getList11(String location1,  String num_type, int age1, int age2, User user, Timestamp date1, Timestamp date2, String gender, Pageable pageable);

        @Query("SELECT b, m FROM Board b LEFT JOIN Bookmark m ON b.idx = m.board.idx AND m.user = ?1 WHERE b.idx = ?2" )
        public List<Object[]> noBoard(User user, Long boardId);

        @Query("SELECT b FROM Board b  WHERE b.gender = ?1 ORDER BY b.createdDate DESC " )
        public List<Board> rec1(String gender);

        @Query("SELECT m FROM Matched m WHERE m.board.idx = ?1 AND m.is_matched=true")
        public Matched recFor(Long boardId);

        @Query("SELECT b FROM Board b  WHERE b.location1 = ?1 AND b.location2 = ?2 AND b.gender = ?3 ORDER BY b.createdDate DESC " )
        public List<Board> rec2(String location1, String location2, String gender);




        @Query("SELECT b FROM Board b WHERE b.img1 = ?1")
        public Board searchImg(String img1);



}
