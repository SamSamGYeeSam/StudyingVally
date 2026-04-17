package com.samsamgyeesam.studyingvally.domain.notice.controller;

import com.samsamgyeesam.studyingvally.domain.notice.dto.TeacherNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.notice.service.TeacherNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherNoticeController {

    private final TeacherNoticeService noticeService;


    // 공지사항 페이지로 이동 -> 전체 공지사항 목록 나오는 화면으로
    @GetMapping("/notice")
    public String gotoNoticePage(Model model){

        List<TeacherNoticeDTO> notices = noticeService.findAllNotices();
        model.addAttribute("notices", notices);

        return "course/noticelist";
    }
//   공지사항 누른 경우



}
