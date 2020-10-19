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


}
