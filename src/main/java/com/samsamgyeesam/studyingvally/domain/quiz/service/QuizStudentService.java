package com.samsamgyeesam.studyingvally.domain.quiz.service;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizAttemptDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizEnrolledCourseDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizAttempt;
import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizChapter;
import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizQuiz;
import com.samsamgyeesam.studyingvally.domain.quiz.repository.QuizAttemptRepository;
import com.samsamgyeesam.studyingvally.domain.quiz.repository.QuizChapterRepository;
import com.samsamgyeesam.studyingvally.domain.quiz.repository.QuizEnrollmentRepository;
import com.samsamgyeesam.studyingvally.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizStudentService {

    private final QuizEnrollmentRepository quizEnrollmentRepository;
    private final QuizAttemptRepository quizAttemptRepository;

    // ✨ 강의 -> 챕터 -> 퀴즈 탐색을 위해 추가 주입
    private final QuizChapterRepository quizChapterRepository;
    private final QuizRepository quizRepository;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<QuizEnrolledCourseDTO> getEnrolledCourses(Long userNo) {
        return quizEnrollmentRepository.findEnrolledCoursesByUserNo(userNo);
    }

    @Transactional(readOnly = true)
    public Map<Long, Integer> getUserQuizScoreMap(Long userNo) {
        List<QuizAttempt> attempts = quizAttemptRepository.findByUserNo(userNo);
        return attempts.stream()
                .collect(Collectors.toMap(
                        attempt -> Long.valueOf(attempt.getQuizNo()),
                        QuizAttempt::getQuizScore,
                        (existing, replacement) -> existing
                ));
    }

    @Transactional
    public void saveQuizAttempt(QuizAttemptDTO attemptDTO) {
        Optional<QuizAttempt> existingAttemptOpt =
                quizAttemptRepository.findByQuizNoAndUserNo(attemptDTO.getQuizNo(), attemptDTO.getUserNo());

        if (existingAttemptOpt.isPresent()) {
            QuizAttempt existingAttempt = existingAttemptOpt.get();
            modelMapper.map(attemptDTO, existingAttempt);
            quizAttemptRepository.save(existingAttempt);
        } else {
            QuizAttempt newAttempt = modelMapper.map(attemptDTO, QuizAttempt.class);
            quizAttemptRepository.save(newAttempt);
        }
    }

    // ==========================================
    // ✨ [신규 추가] 특정 강의의 평균 점수 계산 로직
    // ==========================================
    @Transactional(readOnly = true)
    public double getCourseAverageScore(Long courseId, Long userNo) {
        // 1. 강의에 속한 모든 챕터 조회
        List<QuizChapter> chapters = quizChapterRepository.findByCourseId(courseId);

        // 2. 챕터들에 속한 모든 퀴즈 번호(quizNo) 수집
        List<Long> quizNosInCourse = new ArrayList<>();
        for (QuizChapter chapter : chapters) {
            List<QuizQuiz> quizzes = quizRepository.findByChapNo(chapter.getChapNo());
            for (QuizQuiz quiz : quizzes) {
                quizNosInCourse.add(quiz.getQuizNo());
            }
        }

        // 퀴즈가 하나도 없다면 0점 처리
        if (quizNosInCourse.isEmpty()) {
            return 0.0;
        }

        // 3. 해당 유저가 푼 '모든' 퀴즈 기록 조회
        List<QuizAttempt> attempts = quizAttemptRepository.findByUserNo(userNo);

        // 4. 이 강의(Course)에 속한 퀴즈들의 점수만 골라서 합산
        int totalEarnedScore = 0;
        for (QuizAttempt attempt : attempts) {
            Long attemptQuizNo = Long.valueOf(attempt.getQuizNo());
            if (quizNosInCourse.contains(attemptQuizNo)) {
                totalEarnedScore += attempt.getQuizScore();
            }
        }

        // 5. 평균 계산: (내가 얻은 총 점수 / (전체 퀴즈 개수 * 100)) * 100
        // 결국 수식은 "총 점수 / 전체 퀴즈 개수" 와 동일합니다.
        return (double) totalEarnedScore / quizNosInCourse.size();
    }
}