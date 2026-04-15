package com.samsamgyeesam.studyingvally.domain.course.controller;

import com.samsamgyeesam.studyingvally.domain.course.dto.StudentReviewResponseDTO;
import com.samsamgyeesam.studyingvally.domain.course.service.StudentReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student/reviews")
@RequiredArgsConstructor
public class StudentReviewController {

    private final StudentReviewService studentReviewService;


    @GetMapping("/{courseId}")
    public List<StudentReviewResponseDTO> getReviews(@PathVariable Long courseId) {
        List<StudentReviewResponseDTO> reviews = studentReviewService.getReviewsByCourseId(courseId);
        return reviews;
    }

}
