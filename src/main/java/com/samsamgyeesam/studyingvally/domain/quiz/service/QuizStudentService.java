package com.samsamgyeesam.studyingvally.domain.quiz.service;

import com.samsamgyeesam.studyingvally.domain.quiz.dto.QuizEnrolledCourseDTO;
import com.samsamgyeesam.studyingvally.domain.quiz.repository.QuizEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizStudentService {

    private final QuizEnrollmentRepository quizEnrollmentRepository;

    // 사용자 번호(user_no)를 통해 수강 강의 목록 조회
    @Transactional(readOnly = true)
    public List<QuizEnrolledCourseDTO> getEnrolledCourses(Long userNo) {
        return quizEnrollmentRepository.findEnrolledCoursesByUserNo(userNo);
    }
}