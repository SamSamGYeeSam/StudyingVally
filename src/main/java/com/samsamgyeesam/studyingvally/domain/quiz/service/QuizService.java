package com.samsamgyeesam.studyingvally.domain.quiz.service;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizListDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizList;
import com.samsamgyeesam.studyingvally.domain.quiz.repository.QuizListRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizListRepository quizListRepository;
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


}
