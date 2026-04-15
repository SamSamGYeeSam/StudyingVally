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
    private final QuizChapterRepository quizChapterRepository;
    private final ModelMapper modelMapper;

    // ==========================================
    // [등록 로직]
    // ==========================================
    @Transactional
    public Long registQuizList(QuizListDTO quizListDTO) {
        try {
            QuizQuizList quizQuizList = modelMapper.map(quizListDTO, QuizQuizList.class);
            quizListRepository.save(quizQuizList);
            return quizQuizList.getQuizListNo(); // Long 반환
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Transactional
    public Long registQuiz(QuizDTO quizDTO) {
        try {
            QuizQuiz quizQuiz = modelMapper.map(quizDTO, QuizQuiz.class);
            quizRepository.save(quizQuiz);
            return quizQuiz.getQuizNo(); // Long 반환 (만약 entity가 String이라면 수정 필요)
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Transactional
    public Long registQuizChapter(QuizChapterDTO quizChapterDTO) {
        try {
            QuizChapter quizChapter = modelMapper.map(quizChapterDTO, QuizChapter.class);
            quizChapterRepository.save(quizChapter);
            return quizChapter.getChapNo(); // Long 반환
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    // ==========================================
    // [조회 및 수정 로직] - 컨트롤러 지원용 추가됨
    // ==========================================

    // 1. 특정 강의의 챕터 목록 조회
    @Transactional(readOnly = true)
    public List<QuizChapterDTO> getQuizChapterListByCourseId(Long courseId) { // Long 적용
        List<QuizChapter> quizChapterList = quizChapterRepository.findByCourseId(courseId);
        return quizChapterList.stream()
                .map(quizChapter -> modelMapper.map(quizChapter, QuizChapterDTO.class))
                .collect(Collectors.toList());
    }

    // 2. 특정 챕터의 퀴즈 목록 조회 (추가됨)
    @Transactional(readOnly = true)
    public List<QuizDTO> getQuizListByChapNo(Long chapNo) { // Long 적용
        List<QuizQuiz> quizzes = quizRepository.findByChapNo(chapNo);
        return quizzes.stream()
                .map(quiz -> modelMapper.map(quiz, QuizDTO.class))
                .collect(Collectors.toList());
    }

    // 3. 특정 퀴즈의 문제 리스트 조회 (추가됨)
    @Transactional(readOnly = true)
    public List<QuizListDTO> getQuizListItemsByQuizNo(String quizNo) {
        List<QuizQuizList> quizLists = quizListRepository.findByQuizNo(quizNo);
        return quizLists.stream()
                .map(item -> modelMapper.map(item, QuizListDTO.class))
                .collect(Collectors.toList());
    }

    // 4. 문제 수정을 위한 단일 문제 조회 (추가됨)
    @Transactional(readOnly = true)
    public QuizListDTO getQuizListItemById(Long quizListNo) { // Long 적용
        QuizQuizList quizList = quizListRepository.findById(quizListNo).orElse(null);
        if (quizList == null) return null;
        return modelMapper.map(quizList, QuizListDTO.class);
    }

    // 5. 퀴즈 문제 수정 처리 (추가됨)
    @Transactional
    public void updateQuizList(QuizListDTO quizListDTO) {
        QuizQuizList entity = modelMapper.map(quizListDTO, QuizQuizList.class);
        quizListRepository.save(entity);
    }
}