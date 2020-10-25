package com.example.meeting.board;


import com.example.meeting.board.Board;
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
//    public List<Board> findByLocationAndNum_typeAndAgeLessThanEqualAndAgeGreaterThanEqual(String location, String num_type, int age1, int age2);
        public Page<Board> findAll(Pageable pageable);

//        public Board findByTitle(Pageable pageable, String title);

        @Query("SELECT b FROM Board b WHERE b.location like ?1")
        public Page<Board> getList1(String location, Pageable pageable);

        @Query("SELECT b FROM Board b WHERE b.num_type like ?1 ")
        public Page<Board> getList2(String num_type, Pageable pageable);

        @Query("SELECT b FROM Board b WHERE b.age <= ?1 and b.age >= ?2")
        public Page<Board> getList3(int age1, int age2, Pageable pageable);

        @Query("SELECT b FROM Board b WHERE b.location like ?1 and b.num_type like ?2")
        public Page<Board> getList4(String location, String num_type, Pageable pageable);

        @Query("SELECT b FROM Board b WHERE b.location like ?1  and b.age <= ?2 and b.age >= ?3")
        public Page<Board> getList5(String location, int age1, int age2, Pageable pageable);

        @Query("SELECT b FROM Board b WHERE b.num_type like ?1 and b.age <= ?2 and b.age >= ?3")
        public Page<Board> getList6( String num_type, int age1, int age2, Pageable pageable);

        @Query("SELECT b FROM Board b WHERE b.location like ?1 and b.num_type like ?2 and b.age <= ?3 and b.age >= ?4")
        public Page<Board> getList7(String location, String num_type, int age1, int age2, Pageable pageable);

        @Query("SELECT b.idx FROM Board b WHERE b.updatedDate = ( select Max(a.createdDate) from Board a )")
        public Integer test();



}
