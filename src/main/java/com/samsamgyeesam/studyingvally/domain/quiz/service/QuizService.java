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

    // 100점 만점 검증로직
    @Transactional
    public void registQuizListBatch(List<QuizListDTO> quizListDTOs) {
        // 1. 5문제 검증 로직
        if (quizListDTOs == null || quizListDTOs.size() < 5) {
            throw new IllegalArgumentException("서바이벌 퀴즈 밸런스를 위해 최소 5개 이상의 문제를 등록해야 합니다. (현재: " + (quizListDTOs == null ? 0 : quizListDTOs.size()) + "개)");
        }

        // 2. 배점 총합 100점 검사
        long totalScore = quizListDTOs.stream()
                .mapToLong(dto -> dto.getQuizScore() != null ? dto.getQuizScore() : 0L).sum();

        if (totalScore != 100L) {
            throw new IllegalArgumentException("문제 배점의 총합은 정확히 100점이어야 합니다. (현재 합계: " + totalScore + "점)");
        }

        List<QuizQuizList> entities = quizListDTOs.stream()
                .map(dto -> modelMapper.map(dto, QuizQuizList.class))
                .collect(Collectors.toList());

        quizListRepository.saveAll(entities);
    }

    @Transactional
    public Long registQuizList(QuizListDTO quizListDTO) {
        try {
            QuizQuizList quizQuizList = modelMapper.map(quizListDTO, QuizQuizList.class);
            quizListRepository.save(quizQuizList);
            return quizQuizList.getQuizListNo();
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
            return quizQuiz.getQuizNo();
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
            return quizChapter.getChapNo();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    // ==========================================
    // [조회 및 수정 로직]
    // ==========================================
    @Transactional(readOnly = true)
    public List<QuizChapterDTO> getQuizChapterListByCourseId(Long courseId) {
        List<QuizChapter> quizChapterList = quizChapterRepository.findByCourseId(courseId);
        return quizChapterList.stream()
                .map(quizChapter -> modelMapper.map(quizChapter, QuizChapterDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuizDTO> getQuizListByChapNo(Long chapNo) {
        List<QuizQuiz> quizzes = quizRepository.findByChapNo(chapNo);
        return quizzes.stream()
                .map(quiz -> modelMapper.map(quiz, QuizDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuizListDTO> getQuizListItemsByQuizNo(String quizNo) {
        List<QuizQuizList> quizLists = quizListRepository.findByQuizNo(quizNo);
        return quizLists.stream()
                .map(item -> modelMapper.map(item, QuizListDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public QuizListDTO getQuizListItemById(Long quizListNo) {
        QuizQuizList quizList = quizListRepository.findById(quizListNo).orElse(null);
        if (quizList == null) return null;
        return modelMapper.map(quizList, QuizListDTO.class);
    }

    @Transactional
    public void updateQuizList(QuizListDTO quizListDTO) {
        QuizQuizList entity = modelMapper.map(quizListDTO, QuizQuizList.class);
        quizListRepository.save(entity);
    }

    @Transactional
    public void updateQuizListBatch(List<QuizListDTO> quizListDTOs) {
        if (quizListDTOs == null || quizListDTOs.size() < 5) {
            throw new IllegalArgumentException("서바이벌 퀴즈 밸런스를 위해 최소 5개 이상의 문제를 유지해야 합니다. (현재: " + (quizListDTOs == null ? 0 : quizListDTOs.size()) + "개)");
        }

        long totalScore = quizListDTOs.stream()
                .mapToLong(dto -> dto.getQuizScore() != null ? dto.getQuizScore() : 0L).sum();

        if (totalScore != 100L) {
            throw new IllegalArgumentException("수정 시에도 문제 배점의 총합은 정확히 100점이어야 합니다. (현재: " + totalScore + "점)");
        }

        // 객체가 아닌 String 타입의 퀴즈 번호를 그대로 꺼냅니다.
        String quizNo = quizListDTOs.get(0).getQuizNo();

        // 1. DB에 저장되어 있던 원래 문제 목록을 전부 불러옵니다.
        List<QuizQuizList> existingEntities = quizListRepository.findByQuizNo(quizNo);

        // 2. 강사가 수정한 폼에서 넘어온 문제들의 PK(quizListNo) 목록을 모읍니다.
        java.util.Set<Long> incomingIds = quizListDTOs.stream()
                .map(QuizListDTO::getQuizListNo)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());

        // 3. [DELETE 처리] 기존 문제 중, 폼에서 날아오지 않은 문제(강사가 삭제한 문제)를 찾아서 지웁니다.
        List<QuizQuizList> itemsToDelete = existingEntities.stream()
                .filter(entity -> !incomingIds.contains(entity.getQuizListNo()))
                .collect(Collectors.toList());
        quizListRepository.deleteAll(itemsToDelete);

        // 4. [UPDATE & INSERT 처리] 남은 문제들을 저장합니다.
        List<QuizQuizList> entitiesToSave = quizListDTOs.stream().map(dto -> new QuizQuizList(
                dto.getQuizListNo(),
                dto.getQuizTitle(),
                dto.getQuizDesc(),
                dto.getQuizAnswer(),
                dto.getQuizAnswerDesc(),
                dto.getQuizScore(),
                quizNo
        )).collect(Collectors.toList());

        quizListRepository.saveAll(entitiesToSave);
    }
}