package com.example.meeting.dto.match;


import com.example.meeting.dto.filter.BoardDto;
import com.example.meeting.dto.user.UserDto;
import lombok.Data;

@Data
public class MatchDto {
    Long idx;
    UserDto sender;
    BoardDto board;
    boolean status;
    boolean is_matched;
}
