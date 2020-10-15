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

    private String keyword;

    private String location;

    private String num_type;

    private int age;

    private String gender;

    private Timestamp createdDate;

    private Timestamp updatedDate;

    private User user;

}
