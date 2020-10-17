package com.example.meeting.board;


import com.example.meeting.board.Board;
import com.example.meeting.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BoardRepository extends JpaRepository<Board, Long> {

//    public List<Board> findByLocationAndNum_typeAndAgeLessThanEqualAndAgeGreaterThanEqual(String location, String num_type, int age1, int age2);
        public List<Board> findAll();

        public Board findByTitle(String title);

        @Query("SELECT b FROM Board b WHERE b.location like ?1")
        public List<Board> getList1(String location);

        @Query("SELECT b FROM Board b WHERE b.num_type like ?1 ")
        public List<Board> getList2(String num_type);

        @Query("SELECT b FROM Board b WHERE b.age <= ?1 and b.age >= ?2")
        public List<Board> getList3(int age1, int age2);

        @Query("SELECT b FROM Board b WHERE b.location like ?1 and b.num_type like ?2")
        public List<Board> getList4(String location, String num_type);

        @Query("SELECT b FROM Board b WHERE b.location like ?1  and b.age <= ?2 and b.age >= ?3")
        public List<Board> getList5(String location, int age1, int age2);

        @Query("SELECT b FROM Board b WHERE b.num_type like ?1 and b.age <= ?2 and b.age >= ?3")
        public List<Board> getList6( String num_type, int age1, int age2);

        @Query("SELECT b FROM Board b WHERE b.location like ?1 and b.num_type like ?2 and b.age <= ?3 and b.age >= ?4")
        public List<Board> getList7(String location, String num_type, int age1, int age2);



}
