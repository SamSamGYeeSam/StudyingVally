package com.samsamgyeesam.studyingvally.domain.study.service;

import com.samsamgyeesam.studyingvally.domain.study.dto.StudentDTO;
import com.samsamgyeesam.studyingvally.domain.study.dto.StudentEvaluationResponseDTO;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentEvaluation;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentEnrollmentRepository;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentEvaluationRepository;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentEvaluationService {


    private final StudentEvaluationRepository studentEvaluationRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;


    public List<StudentEvaluationResponseDTO> getEvaluationsByCourseId(Long courseId) {
        List<StudentEvaluation> list = studentEvaluationRepository.findByStudentCourse_CourseId(courseId);
    return list.stream().map(evaluation -> {
        String nickname = (evaluation.getUser() != null) ? evaluation.getUser().getUserNickname() : "익명";

        return StudentEvaluationResponseDTO.builder()
                .content(evaluation.getEvaluationDesc())
                .score(evaluation.getEvaluationScore())
                .nickname(nickname)
                .build();
    }).collect(Collectors.toList());
    }

    @Transactional
    public void saveStudentEvaluation(Long userNo, Long courseId, int rating, String content) {
        StudentEvaluation studentEvaluation = StudentEvaluation.builder()
                .userNo(userNo)
                .courseId(courseId)
                .evaluationScore((double) rating)
                .evaluationDesc(content)
                .build();

        studentEvaluationRepository.save(studentEvaluation);
    }

    // StudentEvaluationService.java
    public int getProgress(Long userNo, Long courseId) {
        return studentEnrollmentRepository.findByUserNoAndCourse_CourseId(userNo, courseId)
                .map(en -> en.getEnrollmentProcess().intValue()) // Double을 int로 변환
                .orElse(0); // 수강 내역이 없으면 0
    }
}
