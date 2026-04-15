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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


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
    @PostMapping("/register")
    public String registCourse() {
        return "course/registcourse";
    }

    // 강의 받아서 챕터 등록 페이지로 전달
    @PostMapping("/registcourse")
    public String registCourse(@RequestParam String courseTitle,
                               @RequestParam String courseDescription,
                               HttpSession session,
                               Model model) {

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
                                @RequestParam String chapTitle,
                                @RequestParam String chapDesc,
                                @RequestParam("videoFile") MultipartFile videoFile,
                                HttpSession session) {

//        Long userNo = 1L;
         Long userNo = (Long) session.getAttribute("userNo");

        // 강의 등록
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseTitle(courseTitle);
        courseDTO.setCourseDescription(courseDescription);
        courseDTO.setUserNo(userNo);

        Long courseId = courseService.registCourse(courseDTO);

        // 챕터 등록
        ChapterDTO chapterDTO = new ChapterDTO();
        chapterDTO.setChapTitle(chapTitle);
        chapterDTO.setChapDesc(chapDesc);
        chapterDTO.setCourseId(courseId);

        // 영상 저장
        String videoUrl = saveVideoFile(videoFile);
        chapterDTO.setChapUrl(videoUrl);

        chapterService.registChapter(chapterDTO);

        // 저장 완료 후
        return "course/registcomplete";
    }

    // 영상 파일 저장
    private String saveVideoFile(MultipartFile videoFile) {
        if (videoFile.isEmpty()) {
            return null;
        }

        // 실제로는 파일을 서버에 저장하고 경로 반환
        // 여기서는 임시로 파일명만 반환
        String fileName = videoFile.getOriginalFilename();

        // TODO: 실제 파일 저장 로직 구현
        // 예: /uploads/videos/파일명.mp4

        return "/uploads/videos/" + fileName;
    }


}
