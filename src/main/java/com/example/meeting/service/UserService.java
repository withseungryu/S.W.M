package com.example.meeting.service;

import com.example.meeting.dao.BoardRepository;
import com.example.meeting.dao.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.sql.Date;

@AllArgsConstructor
@Service
public class UserService {

    UserRepository userRepository;
    BoardRepository boardRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cronJobSch() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        java.util.Date now = new java.util.Date();
        String strDate = sdf.format(now);
        System.out.println("Java cron job expression:: " + strDate);
        userRepository.changePoint();
        //        List<User> user = userRepository.findAll();
//        for(int i=0; i<user.size(); ++i){
//            user.get(i).setPoint(10);
//            userRepository.save(user.get(i));
//        }
        boardRepository.deleteBoard((new Date(System.currentTimeMillis())));
    }
}