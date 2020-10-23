package com.example.meeting.user;

import lombok.Data;

@Data
public class CheckAnswer {
    int code;
    String msg;
    boolean is_checked;

    public void setCheckAnswer(int code, String msg, boolean is_checked){
        this.code =code;
        this.msg = msg;
        this.is_checked = is_checked;
    }
}
