package com.example.meeting.board.dto;

import com.example.meeting.user.User;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class BoardDto {

    private Long idx;

    private String title;

    private String img1;

    private String img2;


    private String img3;


    private String tag1;

    private String tag2;


    private String tag3;


    private String location1;

    private String location2;

    private String num_type;

    private int age;

    private String gender;

    private Date date;

    private Timestamp createdDate;

    private Timestamp updatedDate;

    private User user;

    public boolean check;

    public void setBookAll(Long idx, String title, String img1, String img2, String img3, String tag1, String tag2,
                  String tag3, String location1, String location2,  String num_type, int age, String gender, Date date,
                          Timestamp createdDate,
                  Timestamp updatedDate, User user, boolean check){

        this.idx = idx;
        this.title = title;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.location1 =location1;
        this.location2 = location2;
        this.num_type = num_type;
        this.age = age;
        this.gender = gender;
        this.date = date;
        this.createdDate =createdDate;
        this.updatedDate = updatedDate;
        this.user = user;
        this.check = check;
    }

    public void cloneBoard(BoardDto boardDto){
        this.idx = boardDto.getIdx();
        this.title = boardDto.getTitle();
        this.img1 = boardDto.getImg1();
        this.img2 = boardDto.getImg2();
        this.img3 = boardDto.getImg3();
        this.tag1 = boardDto.getTag1();
        this.tag2 = boardDto.getTag2();
        this.tag3 = boardDto.getTag3();
        this.location1 = boardDto.getLocation1();
        this.location2 = boardDto.getLocation2();
        this.num_type = boardDto.getNum_type();;
        this.age = boardDto.getAge();
        this.gender = boardDto.getGender();
        this.date = boardDto.getDate();
        this.createdDate = boardDto.getCreatedDate();
        this.updatedDate = boardDto.getUpdatedDate();;
        this.user = boardDto.getUser();
        this.check = boardDto.check;
    }

}
