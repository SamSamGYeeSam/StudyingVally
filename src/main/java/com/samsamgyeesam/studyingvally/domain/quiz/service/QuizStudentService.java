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
    private final QuizChapterRepository quizChapterRepository;
    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<QuizEnrolledCourseDTO> findEnrolledCourses(Long userNo) {
        return quizEnrollmentRepository.findEnrolledCoursesByUserNo(userNo);
    }

    @Transactional(readOnly = true)
    public Map<Long, Integer> findUserQuizScoreMap(Long userNo) {
        List<QuizAttempt> attempts = quizAttemptRepository.findByUserNo(userNo);
        // 이미 Long 타입이므로 Long.valueOf 제거
        return attempts.stream()
                .collect(Collectors.toMap(
                        QuizAttempt::getQuizNo,
                        QuizAttempt::getQuizScore,
                        (existing, replacement) -> existing
                ));
    }

    @Transactional
    public void registQuizAttempt(QuizAttemptDTO attemptDTO) {
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

    @Transactional(readOnly = true)
    public double findCourseAverageScore(Long courseId, Long userNo) {
        List<QuizChapter> chapters = quizChapterRepository.findByCourseId(courseId);
        List<Long> quizNosInCourse = new ArrayList<>();

        for (QuizChapter chapter : chapters) {
            List<QuizQuiz> quizzes = quizRepository.findByChapNo(chapter.getChapNo());
            for (QuizQuiz quiz : quizzes) {
                quizNosInCourse.add(quiz.getQuizNo());
            }
        }

        if (quizNosInCourse.isEmpty()) {
            return 0.0;
        }

        List<QuizAttempt> attempts = quizAttemptRepository.findByUserNo(userNo);
        int totalEarnedScore = 0;

        for (QuizAttempt attempt : attempts) {
            // 이미 Long 타입이므로 바로 가져옴
            Long attemptQuizNo = attempt.getQuizNo();
            if (quizNosInCourse.contains(attemptQuizNo)) {
                totalEarnedScore += attempt.getQuizScore();
            }
        }

        return (double) totalEarnedScore / quizNosInCourse.size();
    }
}