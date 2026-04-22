package com.samsamgyeesam.studyingvally.domain.study.service;

import com.samsamgyeesam.studyingvally.domain.study.dto.StudentEvaluationResponseDTO;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentCourse;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentCourseNotice;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentEnrollment;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentEvaluation;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentCourseRepository;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentEnrollmentRepository;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentEvaluationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentSchoolService {

    private final StudentEvaluationRepository studentEvaluationRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;

    public List<StudentEvaluationResponseDTO> getCourseEvaluations(Long courseId) {
        List<StudentEvaluation> studentEvaluations = studentEvaluationRepository.findByStudentCourse_CourseId(courseId);
        return studentEvaluations.stream()
                .map(eval -> StudentEvaluationResponseDTO.builder()
                        .score(eval.getEvaluationScore())
                        .content(eval.getEvaluationDesc())
                        .nickname(eval.getUser() != null ? eval.getUser().getUserNickname() : "알 수 없음")                        .build())
                .collect(Collectors.toList());

    }

    @Transactional
    public void registerCourse(Long userNo, Long courseId) {

        StudentCourse course = studentCourseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다. ID: " + courseId));

        StudentEnrollment studentEnrollment = new StudentEnrollment();
        studentEnrollment.setUserNo(userNo);
        studentEnrollment.setCourse(course);
        studentEnrollment.setEnrollmentProcess(0.0);

        studentEnrollmentRepository.save(studentEnrollment);
    }

}
