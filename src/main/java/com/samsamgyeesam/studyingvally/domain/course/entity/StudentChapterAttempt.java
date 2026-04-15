package com.samsamgyeesam.studyingvally.domain.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chapter_attempt")
@Getter
@Setter
public class StudentChapterAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chapterAttemptId;

    private Long userNo;
    private Long chapNo;
}
