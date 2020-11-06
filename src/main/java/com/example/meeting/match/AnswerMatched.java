package com.example.meeting.match;

import lombok.Data;

@Data
public class AnswerMatched {
    int code;
    String msg;
    boolean status;

    public void setAnswer(int code, String msg, boolean status){
        this.code = code;
        this.msg = msg;
        this.status = status;
    }
}

