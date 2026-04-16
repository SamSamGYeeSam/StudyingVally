package com.samsamgyeesam.studyingvally.domain.course.controller;

import com.samsamgyeesam.studyingvally.domain.course.dto.ChapterDTO;
import com.samsamgyeesam.studyingvally.domain.course.dto.CourseDTO;
import com.samsamgyeesam.studyingvally.domain.course.service.ChapterService;
import com.samsamgyeesam.studyingvally.domain.course.service.CourseService;
import com.samsamgyeesam.studyingvally.domain.course.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class ChapterController {

    private final CourseService courseService;
    private final ChapterService chapterService;
    private final EnrollmentService enrollmentService;

    // 상세 챕터 보기
    // 선택창에서 오는 경우 -> post 방식
    // 챕터 등록 후 넘어오는 경우 -> get
    @RequestMapping(value = "/course/chapter", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewChapters(@RequestParam Long courseId, Model model) {
        List<ChapterDTO> chapterList = chapterService.findChaptersByCourseId(courseId);
        CourseDTO course = courseService.findCourseById(courseId);

        model.addAttribute("chapterList", chapterList);
        model.addAttribute("course", course);
        model.addAttribute("courseId", courseId);
        return "course/chapterlist";
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



    // 챕터 삭제 확인 페이지
    @PostMapping("/course/deletechaptercheck")
    public String deleteChapterCheck(@RequestParam Long chapNo,
                                     @RequestParam Long courseId,
                                     Model model) {
        ChapterDTO chapter = chapterService.findChapterByChapNo(chapNo);
        CourseDTO course = courseService.findCourseById(courseId);

        model.addAttribute("chapter", chapter);
        model.addAttribute("course", course);

        return "course/deletechapter";
    }


    // 챕터 삭제 처리
    @PostMapping("/course/deletechapter")
    public String deleteChapter(@RequestParam Long chapNo,
                                @RequestParam Long courseId,
                                Model model) {

        // 삭제 전 챕터 제목 가져오기
        ChapterDTO chapter = chapterService.findChapterByChapNo(chapNo);
        String chapTitle = chapter.getChapTitle();

        // 챕터 삭제
        chapterService.deleteChapter(chapNo);

        // 챕터 목록 다시 조회
        List<ChapterDTO> chapterList = chapterService.findChaptersByCourseId(courseId);
        CourseDTO course = courseService.findCourseById(courseId);

        model.addAttribute("chapterList", chapterList);
        model.addAttribute("course", course);
        model.addAttribute("courseId", courseId);
        model.addAttribute("successMessage", chapTitle + " 챕터가 삭제되었습니다.");

        return "course/chapterlist";
    }


}
