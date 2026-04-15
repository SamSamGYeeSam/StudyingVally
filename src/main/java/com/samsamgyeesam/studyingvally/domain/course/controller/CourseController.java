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
    // 상세 챕터 보기
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
    @GetMapping("/course/list")
    @ResponseBody
    public List<CourseDTO> getCourseList(HttpSession session) {
        Long userNo = (Long) session.getAttribute("userNo");
//
//        return courseService.findAllCoursesByUserNo(userNo);
        // 임시로 userNo = 1
//        Long userNo = 1L;

        List<CourseDTO> result = courseService.findAllCoursesByUserNo(userNo);

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
    public String updateCoursePage(@RequestParam Long courseId, Model model) {
        CourseDTO course = courseService.findCourseById(courseId);
        model.addAttribute("course", course);
        return "course/updatecourse";
    }

    // 강의 수정 처리
    @PostMapping("/course/updatecourse")
    public String updateCourse(@RequestParam Long courseId,
                               @RequestParam String courseTitle,
                               @RequestParam String courseDescription) {

        courseService.modifyCourse(courseId, courseTitle, courseDescription);

        return "redirect:/teacher/course";
    }

    // 챕터랑 해당 챕터가 속한 강의 정보 가지고 경로롤 이동
    @PostMapping("/course/chapterupdate")
    public String updateChapterPage(@RequestParam Long courseId,
                                    @RequestParam Long chapNo,
                                    Model model) {
        ChapterDTO chapter = chapterService.findChapterByChapNo(chapNo);

        model.addAttribute("chapter", chapter);
        model.addAttribute("courseId", courseId);

        return "course/updatechapter";
    }

    // 챕터 수정 처리
    @PostMapping("/course/updatechapter")
    public String updateChapter(@RequestParam Long chapNo,
                                @RequestParam Long courseId,
                                @RequestParam String chapTitle,
                                @RequestParam String chapDesc,
                                @RequestParam String chapUrl) {

        chapterService.modifyChapter(chapNo, chapTitle, chapDesc, chapUrl);

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
    public String deleteCourse(@RequestParam Long courseId){
        courseService.deleteCourse(courseId);
        return "redirect:/teacher/course"; // 삭제된 거 반영된 강의 전체 목록으로 이동
    }
}