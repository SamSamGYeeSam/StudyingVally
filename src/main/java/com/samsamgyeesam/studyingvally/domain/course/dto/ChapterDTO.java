package com.samsamgyeesam.studyingvally.domain.course.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChapterDTO {

    private Long chapNo;
    private String chapTitle;
    private String chapDesc;
    private String chapUrl;
    private Long courseId;
}
