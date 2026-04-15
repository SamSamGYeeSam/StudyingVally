package com.samsamgyeesam.studyingvally.domain.quiz.service;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizChapterDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizListDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizChapter;
import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizQuiz;
import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizQuizList;
import com.samsamgyeesam.studyingvally.domain.quiz.repository.QuizChapterRepository;
import com.samsamgyeesam.studyingvally.domain.quiz.repository.QuizListRepository;
import com.samsamgyeesam.studyingvally.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizListRepository quizListRepository;
    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;
    private final QuizChapterRepository quizChapterRepository;



    //퀴즈 리스트 등록
    @Transactional
    public Integer registQuizList(QuizListDTO quizListDTO) {

        try {
            QuizQuizList quizQuizList = modelMapper.map(quizListDTO, QuizQuizList.class);
            quizListRepository.save(quizQuizList);
            return quizQuizList.getQuizListNo();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
            //서버 오류를 0으로 표시할 것임.
        }
    }

    @Transactional
    public Integer registQuiz(QuizDTO quizDTO) {
        try {
            QuizQuiz quizQuiz = modelMapper.map(quizDTO, QuizQuiz.class);
            quizRepository.save(quizQuiz);
            return quizQuiz.getQuizNo();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 1. 퀴즈 챕터 등록
    @Transactional
    public Integer registQuizChapter(QuizChapterDTO quizChapterDTO) {
        try {
            QuizChapter quizChapter = modelMapper.map(quizChapterDTO, QuizChapter.class);
            quizChapterRepository.save(quizChapter);
            return quizChapter.getChapNo();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 2. 특정 강의의 퀴즈 챕터 목록 조회
    @Transactional(readOnly = true)
    public List<QuizChapterDTO> getQuizChapterListByCourseId(Integer courseId) {

        List<QuizChapter> quizChapterList = quizChapterRepository.findByCourseId(courseId);

        return quizChapterList.stream()
                .map(quizChapter -> modelMapper.map(quizChapter, QuizChapterDTO.class))
                .collect(Collectors.toList());
    }




}
