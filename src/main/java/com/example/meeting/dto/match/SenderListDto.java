package com.example.meeting.dto.match;

import com.example.meeting.entity.Matched;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class SenderListDto {

    String date;
    List<Matched> matched;

    public SenderListDto(Date date, List<Matched> matched){
        this.date = date.toString();
        List<Matched> nMatcheds = matched;
        this.matched = nMatcheds;
    }

    public void setSenderList(Date date, List<Matched> matched){
        this.date = date.toString();
        List<Matched> nMatcheds = matched;
        this.matched = nMatcheds;
    }

}
