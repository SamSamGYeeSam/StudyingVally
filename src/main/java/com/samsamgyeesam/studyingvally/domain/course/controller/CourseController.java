package com.samsamgyeesam.studyingvally.domain.course.controller;

import com.samsamgyeesam.studyingvally.domain.course.dto.ChapterDTO;
import com.samsamgyeesam.studyingvally.domain.course.dto.CourseDTO;
import com.samsamgyeesam.studyingvally.domain.course.service.ChapterService;
import com.samsamgyeesam.studyingvally.domain.course.service.CourseService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class CourseController {
    // 강의 조회/삭제/수정 클래스
    // 수강생 보기 / 강의평 보기

    private final CourseService courseService;
    private final ChapterService chapterService;

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
    public String showCourseList(){
        return "course/courselist";
    }

    // 강의 전체 목록 띄우기 - db에서 받아서
    @GetMapping("/course/list") // courselist.html 에서 경로 지정
    @ResponseBody //
    public List<CourseDTO> getCourseList(HttpSession session) {
        Long userNo = (Long) session.getAttribute("userNo");
//
//        return courseService.findAllCoursesByUserNo(userNo);
        // 임시로 userNo = 1 고정 (teacher01)
//        Long userNo = 1L;

        System.out.println("============ 강의 목록 조회 시작 ============");
        System.out.println("userNo: " + userNo);

        List<CourseDTO> result = courseService.findAllCoursesByUserNo(userNo);

        System.out.println("조회된 강의 개수: " + result.size());
        System.out.println("============ 강의 목록 조회 완료 ============");

        return result;
    }


//    위에는 강의 목록 보기 누른 경우
//    =================================
//    아래는 하나의 강의 눌렀을 때 창 뜨고 그 창에서 하고자는 것 선택하는 경우


    // 상세 챕터 보기
    @PostMapping("/course/chapter")
    public String viewChapters(@RequestParam Long courseId, Model model) {
        List<ChapterDTO> chapterList = chapterService.findChaptersByCourseId(courseId);
        model.addAttribute("chapterList", chapterList);
        model.addAttribute("courseId", courseId);
        return "course/chapterlist";
    }


    // 강의평 보기
    @PostMapping("/course/review")
    public String viewReviews(@RequestParam Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "course/reviewlist";
    }

    // 수강생 보기
    @PostMapping("/course/studentlist")
    public String viewStudents(@RequestParam Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "course/studentlist";
    }

    // 수정하기
    @PostMapping("/course/update")
    public String update(@RequestParam Long courseId, Model model) {
        CourseDTO course = courseService.findCourseById(courseId);
        model.addAttribute("course", course);
        return "course/update";
    }

    @PostMapping("/course/updatecourse")
    public String updateCourse(@RequestParam Long courseId,
                               @RequestParam String courseTitle,
                               @RequestParam String courseDescription,
                               @RequestParam String courseStatus) {

        courseService.modifyCourse(courseId, courseTitle, courseDescription, courseStatus);

        return "redirect:/teacher/course";
    }

    // 챕터 수정
    @PostMapping("/course/updatechapter")
    public String updateChapterPage(@RequestParam Long courseId, Model model) {
        CourseDTO course = courseService.findCourseById(courseId);
        List<ChapterDTO> chapterList = chapterService.findChaptersByCourseId(courseId);

        model.addAttribute("course", course);
        model.addAttribute("chapterList", chapterList);

        return "course/updatechapter";  // 챕터 수정 페이지
    }


    // 강의 삭제 확인 페이지
    @PostMapping("/course/deletecheck")
    public String deleteCheck(@RequestParam Long courseId, Model model) {
        CourseDTO course = courseService.findCourseById(courseId);
        model.addAttribute("course", course);
        return "course/deletecourse";
    }

    // 강의 삭제
    @PostMapping("/course/delete")
    public String deleteCourse(@RequestParam Long courseId){
        courseService.deleteCourse(courseId);
        return "redirect:/teacher/course"; // 삭제된 거 반영된 강의 전체 목록으로 이동
    }
}