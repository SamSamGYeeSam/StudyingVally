package com.samsamgyeesam.studyingvally.domain.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/* comment.
 *  관리자 메인 페이지 요청을 처리하는 컨트롤러
 */

@Controller
@RequestMapping("/admin")
public class AdminMainController {


    @GetMapping("/main")
    public String showAdminMainPage() {
        return "admin/main";
    }
}