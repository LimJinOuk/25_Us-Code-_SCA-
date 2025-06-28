package com.jinouk.sca.domain.homecontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HController {

    @GetMapping("/")
    public String home() {return "mainpage";}

    @GetMapping("/mypage")
    public String mypage() {return "mypage";}

    @GetMapping("/login")
    public String login() {return "login";}

    @GetMapping("/register")
    public String register() {return "register";}

    @GetMapping("/schedule")
    public String map(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return "schedule";
        }
        else {return "mainpage";}
    }

    @GetMapping("/map")
    public String map2(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return "map";
        }
        else {return "mainpage";}
    }

    @GetMapping("/chatbot")
    public String chatbot() {return "chatbot";}

    @GetMapping("/test")
    public String test() {
        return "/test.html";
    }
}
