package com.samsamgyeesam.studyingvally.domain.course.service;

import com.samsamgyeesam.studyingvally.domain.course.dto.StudentEvaluationResponseDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.StudentEvaluation;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentEvaluationRepository;
import com.samsamgyeesam.studyingvally.domain.user.repository.StudentUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentEvaluationService {


    private final StudentEvaluationRepository studentEvaluationRepository;
    private final StudentUserRepository studentUserRepository;

    public List<StudentEvaluationResponseDTO> getEvaluationsByCourseId(Long courseId) {
        List<StudentEvaluation> list = studentEvaluationRepository.findByStudentCourse_CourseId(courseId);
    return list.stream().map(evaluation -> { // 매개변수 이름을 evaluation으로 통일
        String nickname = (evaluation.getUser() != null) ? evaluation.getUser().getUserNickname() : "익명";

        return StudentEvaluationResponseDTO.builder()
                .content(evaluation.getEvaluationDesc())   // getEvaluationDesc() 사용
                .score(evaluation.getEvaluationScore())    // getEvaluationScore() 사용
                .nickname(nickname)
                .build();
    }).collect(Collectors.toList());
    }


}
