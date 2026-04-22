package com.samsamgyeesam.studyingvally.domain.admin.adminmain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminMainController {


    @GetMapping("/main")
    public String showAdminMainPage() {

        return "admin/main";
    }
}