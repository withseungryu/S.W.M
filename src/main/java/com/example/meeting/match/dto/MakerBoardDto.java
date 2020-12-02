package com.example.meeting.match.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class MakerBoardDto {

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

    private List<SenderDto> senders;

    public void setAll(Long idx, String title, String img1, String img2, String img3, String tag1, String tag2,
                       String tag3, String location1, String location2,  String num_type, int age, String gender,
                       Date date, Timestamp createdDate,
                       Timestamp updatedDate, List<SenderDto> senderDtoList){
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
        this.date =date;
        this.createdDate =createdDate;
        this.updatedDate = updatedDate;
        List<SenderDto> nList = new ArrayList<>();
        nList.addAll(senderDtoList);
        this.senders = nList;
    }

}
