package com.example.meeting.board;

import com.example.meeting.user.User;
import com.fasterxml.jackson.databind.ser.std.TimeZoneSerializer;
import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Data
public class TestDto {

    private Long idx;

    private String title;

    private String img1;

    private String img2;


    private String img3;


    private String tag1;

    private String tag2;


    private String tag3;


    private String location;

    private String num_type;

    private int age;

    private String gender;

    private Timestamp createdDate;

    private Timestamp updatedDate;

    private User user;

    public boolean check;

    public void setBookAll(Long idx, String title, String img1, String img2, String img3, String tag1, String tag2,
                  String tag3, String location, String num_type, int age, String gender, Timestamp createdDate,
                  Timestamp updatedDate, User user, boolean check){

        this.idx = idx;
        this.title = title;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.location =location;
        this.num_type = num_type;
        this.age = age;
        this.gender = gender;
        this.createdDate =createdDate;
        this.updatedDate = updatedDate;
        this.user = user;
        this.check = check;
    }

    public void cloneBoard(TestDto testDto){
        this.idx = testDto.getIdx();
        this.title = testDto.getTitle();
        this.img1 = testDto.getImg1();
        this.img2 = testDto.getImg2();
        this.img3 = testDto.getImg3();
        this.tag1 = testDto.getTag1();
        this.tag2 = testDto.getTag2();
        this.tag3 = testDto.getTag3();
        this.location = testDto.getLocation();
        this.num_type = testDto.getNum_type();;
        this.age = testDto.getAge();;
        this.gender = testDto.getGender();;
        this.createdDate = testDto.getCreatedDate();
        this.updatedDate = testDto.getUpdatedDate();;
        this.user = testDto.getUser();
        this.check = testDto.check;
    }

}
