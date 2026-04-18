package com.samsamgyeesam.studyingvally.domain.notice.controller;

import com.samsamgyeesam.studyingvally.domain.notice.dto.TeacherCourseNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.notice.dto.TeacherNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.notice.service.TeacherCourseNoticeService;
import com.samsamgyeesam.studyingvally.domain.user.service.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherCourseNoticeController {

    private final TeacherCourseNoticeService courseNoticeService;

    // 강의소식 페이지로 이동 -> 전체 강의소식 목록 나오는 화면으로
    @GetMapping("/coursenotice")
    public String gotoCourseNoticePage(@AuthenticationPrincipal AuthUserDetails userDetails, Model model) {

        Long userNo = userDetails.getUserNo();

        List<TeacherCourseNoticeDTO> courseNotices = courseNoticeService.findCourseNoticeByUserNo(userNo);
        model.addAttribute("courseNotices", courseNotices);


        return "notice/coursenoticelist";
    }
//   강의소식 누른 경우


    // 강의소식 목록에서 상세보기 누른 경우 : 강의먕 / 소식 제목 / 소식 내용
    @PostMapping("/coursenotice/detail")
    public String gotoCourseNoticeDetailPage(@RequestParam Long courseNoticeNo, Model model) {

        TeacherCourseNoticeDTO courseNotice = courseNoticeService.findCourseNoticeById(courseNoticeNo);
        model.addAttribute("courseNotice", courseNotice);


        return "notice/coursenoticedetaillist";
    }
}