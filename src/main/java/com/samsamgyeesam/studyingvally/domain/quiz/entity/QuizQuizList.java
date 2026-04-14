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
@Table(name = "quiz_list")
public class QuizQuizList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_list_no")
    private int quizListNo;

    @Column(name = "quiz_title")
    private String quizTitle;

    @Column(name = "quiz_desc")
    private String quizDesc;

    @Column(name = "quiz_answer")
    private String quizAnswer;

    @Column(name = "quiz_answer_desc")
    private String quizAnswerDesc;

    @Column(name = "quiz_no")
    private String quizNo;

}
