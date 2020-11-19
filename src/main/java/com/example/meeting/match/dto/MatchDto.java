package com.example.meeting.match.dto;


import com.example.meeting.filter.BoardDto;
import com.example.meeting.user.dto.UserDto;
import lombok.Data;

@Data
public class MatchDto {
    Long idx;
    UserDto sender;
    BoardDto board;
    boolean status;
    boolean is_matched;
}
