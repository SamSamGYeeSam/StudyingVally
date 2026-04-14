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
    private int quizNo;

    @Column(name = "quiz_title")
    private String quizTitle;

    @Column(name = "chap_no")
    private int chapNo;

}