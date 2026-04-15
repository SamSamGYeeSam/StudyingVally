package com.samsamgyeesam.studyingvally.domain.course.controller;

import com.samsamgyeesam.studyingvally.domain.course.dto.ChapterDTO;
import com.samsamgyeesam.studyingvally.domain.course.dto.CourseDTO;
import com.samsamgyeesam.studyingvally.domain.course.service.ChapterService;
import com.samsamgyeesam.studyingvally.domain.course.service.CourseService;
import com.samsamgyeesam.studyingvally.domain.course.service.FileService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class CourseRegistController {
    // 강의 및 챕터 등록 클래스
    /*역할
     * 1. /teacher/regist
     * 2. */

    private final CourseService courseService;
    private final ChapterService chapterService;
    private final FileService fileService;

    // 메인화면에서 강의등록 버튼 눌렀을 때 넘어와서 화면 넘어가는 클래스
    @GetMapping("/register")
    public String registCourse(HttpSession session) {

        // 세션 초기화
        session.removeAttribute("tempCourseTitle");
        session.removeAttribute("tempCourseDescription");

        return "course/registcourse";
    }

    // 강의 받아서 챕터 등록 페이지로 전달
    @PostMapping("/registcourse")
    public String registCourse(@RequestParam String courseTitle,
                               @RequestParam String courseDescription,
                               HttpSession session,
                               Model model) {

        // 이전으로 버튼 눌러도 빈칸 안 나오게
        // session 에 담아서 저장
        session.setAttribute("tempCourseTitle", courseTitle);
        session.setAttribute("tempCourseDescription", courseDescription);

        model.addAttribute("courseTitle", courseTitle);
        model.addAttribute("courseDescription", courseDescription);

        return "course/registchapter";
    }

    // 챕터 정보까지 입력 후 DB 저장
    @PostMapping("/registchapter")
    public String registChapter(@RequestParam String courseTitle,
                                @RequestParam String courseDescription,
                                @RequestParam("chapTitle") List<String> chapTitle,
                                @RequestParam("chapDesc") List<String> chapDesc,
                                @RequestParam(value = "videoFile", required = false) List<MultipartFile> videoFiles,
                                HttpSession session) {

//        Long userNo = 1L;
        Long userNo = (Long) session.getAttribute("userNo");

        // 강의 등록
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseTitle(courseTitle);
        courseDTO.setCourseDescription(courseDescription);
        courseDTO.setUserNo(userNo);

        Long courseId = courseService.registCourse(courseDTO);

        // 챕터 여러 개 등록
        for (int i = 0; i < chapTitle.size(); i++) {
            ChapterDTO chapterDTO = new ChapterDTO();
            chapterDTO.setChapTitle(chapTitle.get(i));
            chapterDTO.setChapDesc(chapDesc.get(i));
            chapterDTO.setCourseId(courseId);

            // 영상 파일이 있는 경우에만 저장
            if (videoFiles != null && i < videoFiles.size() && !videoFiles.get(i).isEmpty()) {
                try {
                    String videoUrl = fileService.saveVideoFile(videoFiles.get(i));
                    chapterDTO.setChapUrl(videoUrl);
                } catch (IOException e) {
                    System.err.println("영상 파일 저장 실패: " + e.getMessage());
                    chapterDTO.setChapUrl(null);
                }
            }
            chapterService.registChapter(chapterDTO);
        }

        // 등록하고 나서 세션 없애기
        session.removeAttribute("tempCourseTitle");
        session.removeAttribute("tempCourseDescription");

        return "redirect:/teacher/course";
    }

    @PostMapping("/registcourse/back")
    public String backToRegistCourse(@RequestParam String courseTitle,
                                     @RequestParam String courseDescription,
                                     Model model) {

        model.addAttribute("courseTitle", courseTitle);
        model.addAttribute("courseDescription", courseDescription);

        return "course/registcourse";
    }

}
