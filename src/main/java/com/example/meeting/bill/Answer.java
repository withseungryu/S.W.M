package com.example.meeting.bill;

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
