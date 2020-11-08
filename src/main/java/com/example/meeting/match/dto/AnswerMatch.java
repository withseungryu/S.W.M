package com.example.meeting.match.dto;

import lombok.Data;

@Data
public class AnswerMatch {
    int code;
    String msg;


    public void setAnswer(int code, String msg){
        this.code = code;
        this.msg = msg;

    }
}
