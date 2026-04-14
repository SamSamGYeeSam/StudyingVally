package com.samsamgyeesam.studyingvally.domain.quiz.service;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizListDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.entity.Quiz;
import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizList;
import com.samsamgyeesam.studyingvally.domain.quiz.repository.QuizListRepository;
import com.samsamgyeesam.studyingvally.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizListRepository quizListRepository;
    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;


    //퀴즈 리스트 등록
    @Transactional
    public Integer registQuizList(QuizListDTO quizListDTO) {

        try {
            QuizList quizList = modelMapper.map(quizListDTO, QuizList.class);
            quizListRepository.save(quizList);
            return quizList.getQuizListNo();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
            //서버 오류를 0으로 표시할 것임.
        }
    }

    @Transactional
    public Integer registQuiz(QuizDTO quizDTO) {
        try {
            Quiz quiz = modelMapper.map(quizDTO, Quiz.class);
            quizRepository.save(quiz);
            return quiz.getQuizNo();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }




}
