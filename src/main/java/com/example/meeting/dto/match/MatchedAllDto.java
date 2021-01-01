package com.example.meeting.dto.match;

import com.example.meeting.entity.Matched;
import lombok.Data;

import java.util.List;

@Data
public class MatchedAllDto {
    List<Matched> is_senders;
    List<Matched> is_makers;
}
