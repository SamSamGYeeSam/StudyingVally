package com.samsamgyeesam.studyingvally.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {

    @GetMapping("/error-page")
    public String errorPage() {
        return "error/error";
    }
}