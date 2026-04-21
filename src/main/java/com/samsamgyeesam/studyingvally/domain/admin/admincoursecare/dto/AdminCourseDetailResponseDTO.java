package com.samsamgyeesam.studyingvally.domain.admin.admincoursecare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;



@Getter
@AllArgsConstructor
public class AdminCourseDetailResponseDTO {

    private Long courseId;
    private String courseTitle;
    private String courseDescription;
    private String courseCreatedAt;
    private String teacherName;
    private String courseStatus;
    private String courseStatusLabel;
    private List<AdminChapterResponseDTO> chapterList;
}