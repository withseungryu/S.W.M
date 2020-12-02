package com.example.meeting.scheduler;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.meeting.user.User;
import com.example.meeting.user.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    UserRepository userRepository;


}
