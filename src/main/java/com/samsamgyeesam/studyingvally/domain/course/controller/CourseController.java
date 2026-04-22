package com.samsamgyeesam.studyingvally.domain.course.controller;

import com.samsamgyeesam.studyingvally.domain.course.dto.ChapterDTO;
import com.samsamgyeesam.studyingvally.domain.course.dto.CourseDTO;
import com.samsamgyeesam.studyingvally.domain.course.dto.EnrollmentDTO;
import com.samsamgyeesam.studyingvally.domain.course.dto.EvaluationDTO;
import com.samsamgyeesam.studyingvally.domain.course.service.*;
import com.samsamgyeesam.studyingvally.domain.user.service.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class CourseController {
    // 강의 조회/삭제/수정 클래스
    // 상세 챕터 보기
    // 수강생 보기 / 강의평 보기

    private final CourseService courseService;
    private final ChapterService chapterService;
    private final EnrollmentService enrollmentService;
    private final EvaluationService evaluationService;
    private final QuestionCourseService questionCourseService;

    // url에 입력해서 경로 이동하는 경우 get방식
    @GetMapping("/teachermain")
    public String gotoTeacherMainGet(){
        return "course/teachermain";
    }

    // form에서 post로 넘어오는 경우
    @PostMapping("/teachermain")
    public String gotoTeacherMainPost(){
        return "course/teachermain";
    }


    // 메인페이지에서 강의 목록 조회 눌렀을 때 넘어와서 화면 넘어가는 클래스
    @GetMapping("/course")
    public String gotoCourseListPage(@AuthenticationPrincipal AuthUserDetails userDetails, Model model) {
        Long userNo = userDetails.getUserNo();

        List<CourseDTO> courseList = courseService.findAllCoursesByUserNo(userNo);
        // 답변 대기 중인 질문 개수 조회
        Map<String, Integer> unansweredQuestions = questionCourseService.getUnansweredQuestionsByUserNo(userNo);

        model.addAttribute("courseList", courseList);
        model.addAttribute("unansweredQuestions", unansweredQuestions);

        return "course/courselist";
    }
//   강의 목록 보기 누른 경우


    @GetMapping("/mypage")
    public String gotoMypage(){
        return "course/mypage";
    }
//    마이페이지 누른 경우


//  ↓ 강의 목록 보기에서 한 강의 선택한 경우 나온 선택창


    // 강의평 보기
    @PostMapping("/course/review")
    public String gotoReviewListPage(@RequestParam Long courseId, Model model) {
        List<EvaluationDTO> evaluations = evaluationService.findEvaluationsByCourseId(courseId);

        model.addAttribute("evaluations", evaluations);
        model.addAttribute("courseId", courseId);
        return "course/evaluationlist";
    }


    // 수강생 보기
    @PostMapping("/course/studentlist")
    public String gotoStudentListPage(@RequestParam Long courseId, Model model) {

        List<EnrollmentDTO> studentList = enrollmentService.findStudentsByCourseId(courseId);

        CourseDTO course = courseService.findCourseById(courseId);

        model.addAttribute("studentList", studentList);
        model.addAttribute("course", course);
        model.addAttribute("courseId", courseId);

        return "course/studentlist";
    }

    // 수정하기
    @PostMapping("/course/update")
    public String update(@RequestParam Long courseId, Model model) {
        // 강의 조회
        CourseDTO course = courseService.findCourseById(courseId);
        // 챕터 조회
        List<ChapterDTO> chapterList = chapterService.findChaptersByCourseId(courseId);
        model.addAttribute("course", course);
        model.addAttribute("chapterList", chapterList);
        return "course/update";
    }

    // 강의 정보 찾아서 강의 수정 페이지로 이동
    @PostMapping("/course/courseupdate")
    public String gotoupdateCoursePage(@RequestParam Long courseId, Model model) {
        CourseDTO course = courseService.findCourseById(courseId);
        model.addAttribute("course", course);
        return "course/updatecourse";
    }

    // 강의 수정 처리
    @PostMapping("/course/updatecourse")
    public String updateCourse(@ModelAttribute CourseDTO courseDTO,
                               RedirectAttributes redirectAttributes) {

        courseService.updateCourse(courseDTO);

        redirectAttributes.addFlashAttribute("successMessage", "강의가 수정되었습니다.");

        return "redirect:/teacher/course";
    }

    // 강의 삭제 확인 페이지
    @PostMapping("/course/deletecheck")
    public String deleteCheck(@RequestParam Long courseId, Model model) {
        CourseDTO course = courseService.findCourseById(courseId);
        model.addAttribute("course", course);
        return "course/deletecourse";
    }

    // 강의 삭제 처리
    @PostMapping("/course/delete")
    public String deleteCourse(@RequestParam Long courseId, RedirectAttributes redirectAttributes){

        String courseTitle = courseService.deleteCourse(courseId);

        // 삭제 완료 메시지 전달
        redirectAttributes.addFlashAttribute("successMessage", courseTitle + " 강의가 완료되었습니다.");

        return "redirect:/teacher/course"; // 강의 목록
    }

}