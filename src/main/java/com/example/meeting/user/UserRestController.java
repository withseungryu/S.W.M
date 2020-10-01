package com.example.meeting.user;


import com.example.meeting.user.UserRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    private UserRepository userRepository;


    public UserRestController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
}
