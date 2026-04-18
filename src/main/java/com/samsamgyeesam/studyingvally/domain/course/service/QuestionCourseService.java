package com.samsamgyeesam.studyingvally.domain.course.service;

import com.samsamgyeesam.studyingvally.domain.course.dto.QuestionCourseDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.Course;
import com.samsamgyeesam.studyingvally.domain.course.entity.QuestionCourse;
import com.samsamgyeesam.studyingvally.domain.course.repository.CourseRepository;
import com.samsamgyeesam.studyingvally.domain.course.repository.QuestionCourseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionCourseService {

    private final QuestionCourseRepository questionRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    // 강의별 질문 조회
    public List<QuestionCourseDTO> findQuestionsByCourseId(Long courseId) {
        List<QuestionCourse> questionList = questionRepository.findByCourseIdOrderByQuestionCourseNoDesc(courseId);

        return questionList.stream()
                .map(question -> {
                    QuestionCourseDTO questionCourseDTO = modelMapper.map(question, QuestionCourseDTO.class);

                    // 강의 제목 가져오기
                    if (question.getCourseId() != null) {
                        Course course = courseRepository.findById(question.getCourseId()).orElse(null);
                        if (course != null) {
                            questionCourseDTO.setCourseTitle(course.getCourseTitle());
                        }
                    }

                    return questionCourseDTO;
                })
                .collect(Collectors.toList());
    }
}
