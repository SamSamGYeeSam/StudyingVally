package com.samsamgyeesam.studyingvally.domain.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "quiz")
public class QuizQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_no")
    private Long quizNo;

    @Column(name = "quiz_title")
    private String quizTitle;

    @Column(name = "chap_no")
    private Long chapNo;

}