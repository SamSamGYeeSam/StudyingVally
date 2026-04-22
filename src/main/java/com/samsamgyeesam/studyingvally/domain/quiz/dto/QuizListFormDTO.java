package com.samsamgyeesam.studyingvally.domain.quiz.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class QuizListFormDTO {
    private Long quizNo; // String -> Long 변경
    private List<QuizListDTO> quizList;
}