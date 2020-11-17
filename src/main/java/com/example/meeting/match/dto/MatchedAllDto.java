package com.example.meeting.match.dto;

import com.example.meeting.match.Matched;
import lombok.Data;

import java.util.List;

@Data
public class MatchedAllDto {
    List<Matched> is_senders;
    List<Matched> is_makers;
}
