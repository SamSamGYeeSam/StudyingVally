package com.samsamgyeesam.studyingvally.domain.quiz.service;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizAttemptDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizEnrolledCourseDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.entity.QuizAttempt;
import com.samsamgyeesam.studyingvally.domain.quiz.repository.QuizAttemptRepository;
import com.samsamgyeesam.studyingvally.domain.quiz.repository.QuizEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizStudentService {

    private final QuizEnrollmentRepository quizEnrollmentRepository;
    private final QuizAttemptRepository quizAttemptRepository; // 추가됨
    private final ModelMapper modelMapper; // 추가됨
    // 사용자 번호(user_no)를 통해 수강 강의 목록 조회
    @Transactional(readOnly = true)
    public List<QuizEnrolledCourseDTO> getEnrolledCourses(Long userNo) {
        return quizEnrollmentRepository.findEnrolledCoursesByUserNo(userNo);
    }

    @Transactional
    public void saveQuizAttempt(QuizAttemptDTO attemptDTO) {
        QuizAttempt attempt = modelMapper.map(attemptDTO, QuizAttempt.class);
        quizAttemptRepository.save(attempt);
    }
}