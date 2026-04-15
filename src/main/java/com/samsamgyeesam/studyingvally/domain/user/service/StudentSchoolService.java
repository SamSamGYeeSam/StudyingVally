package com.samsamgyeesam.studyingvally.domain.user.service;

import com.samsamgyeesam.studyingvally.domain.course.dto.StudentEvaluationResponseDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.StudentCourse;
import com.samsamgyeesam.studyingvally.domain.course.entity.StudentEnrollment;
import com.samsamgyeesam.studyingvally.domain.course.entity.StudentEvaluation;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentCourseRepository;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentEnrollmentRepository;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentEvaluationRepository;
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

    public List<StudentEvaluationResponseDTO> getCourseReviews(Long courseId) {
        List<StudentEvaluation> studentEvaluations = studentEvaluationRepository.findByCourse_CourseId(courseId);

        return studentEvaluations.stream()
                .map(eval -> StudentEvaluationResponseDTO.builder()
                        .score(eval.getEvaluationScore())
                        .content(eval.getEvaluationDesc())
                        .nickname("익명")
                        .build())
                .collect(Collectors.toList());


    }

    @Transactional
    public void registerCourse(Long userNo, Long courseId) {
        StudentCourse course = studentCourseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 없습니다."));

        StudentEnrollment studentEnrollment = new StudentEnrollment();
        studentEnrollment.setUserNo(userNo);
        studentEnrollment.setCourse(course);
        studentEnrollment.setEnrollmentProcess(0.0);

        studentEnrollmentRepository.save(studentEnrollment);
    }

}
