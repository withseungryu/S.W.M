package com.example.meeting.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
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
    private String tag1;

    @Column
    private String tag2;

    @Column
    private String tag3;


    @Column
    private String location1;

    @Column
    private String location2;

    @Column
    private String num_type;

    @Column
    private int age;

    @Column
    private String gender;

    @Column
    private Date date;

    @Column
    private Timestamp createdDate;

    @Column
    private Timestamp updatedDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user")
    private User user;


    @Builder
    public Board(String title, String img1, String img2, String img3,
                 String tag1, String tag2, String tag3, String location1, String location2, String num_type, int age, String gender,
                  Date date, Timestamp createdDate, Timestamp updatedDate, User user){

        this.title = title;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.location1 = location1;
        this.location2 = location2;
        this.num_type = num_type;
        this.age = age;
        this.gender = gender;
        this.date =date;
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

    public void setImgs(String img1, String img2, String img3){
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
    }


    public void update(Board board) {
        this.title = board.getTitle();
        this.img1 = board.getImg1();
        this.img2 = board.getImg2();
        this.img3 = board.getImg3();
        this.tag1 = board.getTag1();
        this.tag2 = board.getTag2();
        this.tag3 = board.getTag3();
        this.location1 = board.getLocation1();
        this.location2 = board.getLocation2();
        this.num_type = board.getNum_type();
        this.age = age;
        this.gender = board.getGender();
        this.date =board.getDate();
        this.createdDate = board.getCreatedDate();
        LocalDateTime localDateTime = LocalDateTime.now();
        this.updatedDate = Timestamp.valueOf(localDateTime);
        this.user = board.getUser();
    }
}
