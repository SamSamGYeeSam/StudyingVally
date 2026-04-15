package com.samsamgyeesam.studyingvally.domain.quiz.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuizChapterDTO {
    private Long chapNo;
    private String chapTitle;
    private String chapDesc;
    private String chapUrl;
    private Long courseId;
}