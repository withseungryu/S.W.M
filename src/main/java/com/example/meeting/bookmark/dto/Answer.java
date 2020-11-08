package com.example.meeting.bookmark.dto;

import lombok.Data;

@Data
public class Answer {
    int code;
    String msg;

    public void setAnswer(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
