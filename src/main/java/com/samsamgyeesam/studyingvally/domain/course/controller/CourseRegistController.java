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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String registCourse() {
        return "course/registcourse";
    }

    // 강의 등록
    @PostMapping("/registcourse")
    public String registCourse(@RequestParam String courseTitle,
                               @RequestParam String courseDescription,
                               RedirectAttributes redirectAttributes) {

        Long userNo = 2L;
//        Long userNo = (Long) session.getAttribute("userNo");

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseTitle(courseTitle);
        courseDTO.setCourseDescription(courseDescription);
        courseDTO.setUserNo(userNo);

        courseService.registCourse(courseDTO);

        redirectAttributes.addFlashAttribute("infoMessage", "강의 등록이 완료되었습니다. 강의 목록에서 챕터를 추가하고 '요청하기' 버튼을 눌러주세요. 관리자의 승인이 완료되면 학생들에게 강의가 제공됩니다.");

        // 챕터 쪽으로 보내기
        return "redirect:/teacher/course";
    }


    // 챕터 등록 페이지로 이동
    @PostMapping("/course/registchapterPage")
    public String registChapterPage(@RequestParam Long courseId,
                                Model model) {

        CourseDTO course = courseService.findCourseById(courseId);

        // 기존 챕터 개수 조회
        List<ChapterDTO> existingChapters = chapterService.findChaptersByCourseId(courseId);
        int chapterCount = existingChapters.size();

        model.addAttribute("course", course);
        model.addAttribute("courseId", courseId);
        model.addAttribute("startChapterNumber", chapterCount + 1);

        return "course/registchapter";
    }


    // 챕터 등록 처리
    @PostMapping("/course/registchapter")
    public String registChapter(@RequestParam Long courseId,
                                      @RequestParam("chapTitle") List<String> chapTitle,
                                      @RequestParam("chapDesc") List<String> chapDesc,
                                      @RequestParam(value = "videoFile", required = false) List<MultipartFile> videoFiles,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {

        // db 로 저장
        for (int i = 0; i < chapTitle.size(); i++) {
            ChapterDTO chapterDTO = new ChapterDTO();
            chapterDTO.setChapTitle(chapTitle.get(i));
            chapterDTO.setChapDesc(chapDesc.get(i));
            chapterDTO.setCourseId(courseId);

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

        // 챕터 살세 조회 페이지로
        List<ChapterDTO> chapterList = chapterService.findChaptersByCourseId(courseId);
        CourseDTO course = courseService.findCourseById(courseId);

        model.addAttribute("chapterList", chapterList);
        model.addAttribute("course", course);
        model.addAttribute("courseId", courseId);

        redirectAttributes.addFlashAttribute("successMessage", "챕터가 등록되었습니다.");

        return "course/chapterlist";
    }

}
