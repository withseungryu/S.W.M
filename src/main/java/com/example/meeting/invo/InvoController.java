package com.example.meeting.invo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InvoController {

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("greeting", "서울시립대학교 컴퓨터과학부");
        return "home";
    }

}
