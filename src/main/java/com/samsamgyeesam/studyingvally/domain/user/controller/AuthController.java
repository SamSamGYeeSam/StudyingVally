package com.samsamgyeesam.studyingvally.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @GetMapping("/signup1")
    public String signup1(){
        return "auth/signup1";
    }

}
