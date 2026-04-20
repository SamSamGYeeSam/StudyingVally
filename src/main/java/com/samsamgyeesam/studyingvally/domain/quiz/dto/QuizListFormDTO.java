package com.samsamgyeesam.studyingvally.domain.quiz.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class QuizListFormDTO {
    // HTML의 <input type="hidden" name="quizNo"> 를 받습니다.
    private String quizNo;

    // HTML의 name="quizList[0].quizTitle" 배열 요소들을 한 번에 리스트로 묶어 받습니다.
    private List<QuizListDTO> quizList;
}