package com.samsamgyeesam.studyingvally.domain.study.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_no")
    private Long quizId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chap_no", nullable = false)
    private StudentChapter chapter;

    @Column(name = "quiz_title", nullable = false, length = 200)
    private String quizTitle;

    @Column(name = "quiz_order")
    private Integer quizOrder;

    @Column(name = "total_questions")
    private Integer totalQuestions;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<StudentQuizAttempt> attempts = new ArrayList<>();

}
