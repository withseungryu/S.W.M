package com.example.meeting.invo;

import com.example.meeting.board.BoardRepository;
import com.example.meeting.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InvoController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "home";
    }

}
