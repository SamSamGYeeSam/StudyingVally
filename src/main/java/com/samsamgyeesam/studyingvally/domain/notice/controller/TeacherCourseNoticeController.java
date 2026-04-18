package com.samsamgyeesam.studyingvally.domain.notice.controller;

import com.samsamgyeesam.studyingvally.domain.course.dto.CourseDTO;
import com.samsamgyeesam.studyingvally.domain.course.service.CourseService;
import com.samsamgyeesam.studyingvally.domain.notice.dto.TeacherCourseNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.notice.dto.TeacherNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.notice.service.TeacherCourseNoticeService;
import com.samsamgyeesam.studyingvally.domain.user.service.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class TeacherCourseNoticeController {

    private final TeacherCourseNoticeService courseNoticeService;
    private final CourseService courseService;

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


    // 강의 소식 추가하기 버튼 눌렀을 때 페이지로 이동
    @PostMapping("/coursenotice/registcoursenoticePage")
    public String gotoCourseNoticeRegistPage(@AuthenticationPrincipal AuthUserDetails userDetails, Model model) {

        Long userNo = userDetails.getUserNo();

        // 강사가 자신이 올린 강의 중 강의소식을 올리고자 하는 강의를 선택하게 하려고
        // 소식 등록하면서 강의 정보 같이 넘겨줌
        List<CourseDTO> courseList = courseService.findAllCoursesByUserNo(userNo);
        model.addAttribute("courseList", courseList);

        return "notice/registcoursenotice";
    }

    // 강의 소식 등록 처리
    @PostMapping("/coursenotice/registcoursenotice")
    public String registCourseNotice(@RequestParam Long courseId,
                                     @RequestParam String courseNoticeTitle,
                                     @RequestParam String courseNoticeDesc,
                                     @AuthenticationPrincipal AuthUserDetails userDetails,
                                     RedirectAttributes redirectAttributes) {

        Long userNo = userDetails.getUserNo();

        TeacherCourseNoticeDTO courseNoticeDTO = new TeacherCourseNoticeDTO();
        courseNoticeDTO.setCourseId(courseId);
        courseNoticeDTO.setCourseNoticeTitle(courseNoticeTitle);
        courseNoticeDTO.setCourseNoticeDesc(courseNoticeDesc);
        courseNoticeDTO.setUserNo(userNo);

        courseNoticeService.registCourseNotice(courseNoticeDTO);

        redirectAttributes.addFlashAttribute("successMessage", "강의 소식이 등록되었습니다.");

        return "redirect:/teacher/coursenotice";
    }
}