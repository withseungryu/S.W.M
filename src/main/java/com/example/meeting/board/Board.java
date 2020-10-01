package com.example.meeting.board;


import com.example.meeting.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table
public class Board implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String title;

    @Column
    private String img1;

    @Column
    private String img2;

    @Column
    private String img3;

    @Column
    private String keyword;


    @Column
    private String location;

    @Column
    private int num_type;

    @Column
    private int gender;

    @Column
    private Timestamp createdDate;

    @Column
    private Timestamp updatedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user")
    private User user;




    @Builder
    public Board(String title, String img1, String img2, String img3,
                 String keyword, String location, int num_type, int gender,
                 Timestamp createdDate, Timestamp updatedDate, User user){

        this.title = title;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.keyword = keyword;
        this.location = location;
        this.num_type = num_type;
        this.gender = gender;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.user = user;
    }

    public void setCreatedDateNow(){
        LocalDateTime localDateTime = LocalDateTime.now();
        this.createdDate = Timestamp.valueOf(localDateTime);
    }


    public void setUpdatedDateNow(){
        LocalDateTime localDateTime = LocalDateTime.now();
        this.updatedDate = Timestamp.valueOf(localDateTime);
    }

    public void update(Board board) {
        this.title = board.getTitle();
        this.img1 = board.getImg1();
        this.img2 = board.getImg2();
        this.img3 = board.getImg3();
        this.keyword = board.getKeyword();
        this.location = board.getLocation();
        this.num_type = board.getNum_type();
        this.gender = board.getGender();
        this.createdDate = board.getCreatedDate();
        LocalDateTime localDateTime = LocalDateTime.now();
        this.updatedDate = Timestamp.valueOf(localDateTime);
        this.user = board.getUser();
    }
}
