package com.example.meeting.filter;

import com.example.meeting.user.User;
import lombok.Data;

import javax.persistence.*;
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

    private String location;

    private String num_type;

    private String age;

    private String gender;

    private Long user;

    private String createdDate;

    private String updatedDate;

    public void setAll(Long idx, String title, String img1, String img2, String img3, String tag1,
                           String tag2, String tag3, String location, String num_type, String age, String gender,
                           Long user, String createdDate, String updatedDate){

        this.idx = idx;
        this.title = title;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.location = location;
        this.num_type = num_type;
        this.age = age;
        this.gender = gender;
        this.user = user;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;



    }

}
