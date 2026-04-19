package com.samsamgyeesam.studyingvally.domain.course.service;

import com.samsamgyeesam.studyingvally.domain.course.dto.QuestionCourseDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.Course;
import com.samsamgyeesam.studyingvally.domain.course.entity.QuestionCourse;
import com.samsamgyeesam.studyingvally.domain.course.repository.CourseRepository;
import com.samsamgyeesam.studyingvally.domain.course.repository.QuestionCourseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionCourseService {

    private final QuestionCourseRepository questionRepository;
    private final ModelMapper modelMapper;

    // 강의별 질문 조회
    public List<QuestionCourseDTO> findQuestionsByCourseId(Long courseId) {
        List<QuestionCourse> questionList = questionRepository.findByCourseIdWithCourse(courseId);

        return questionList.stream()
                .map(question -> {
                    QuestionCourseDTO questionCourseDTO = modelMapper.map(question, QuestionCourseDTO.class);

                    // 강의 제목 가져오기
                    if (question.getCourse() != null) {
                        questionCourseDTO.setCourseTitle(question.getCourse().getCourseTitle());
                    }

                    return questionCourseDTO;
                })
                .collect(Collectors.toList());
    }

    // 강사가 답변 달고자 하는 질문의 정보 가져오기
    public QuestionCourseDTO findQuestionById(Long questionCourseNo) {
        QuestionCourse question = questionRepository.findById(questionCourseNo)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        // 위에서 만든 거 쓰지
        return modelMapper.map(question, QuestionCourseDTO.class);
    }

    // 답변 등록 처리
    @Transactional
    public void answerQuestion(Long questionCourseNo, String questionCourseAnswer) {
        QuestionCourse foundQuestion = questionRepository.findById(questionCourseNo)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        foundQuestion.answerQuestion(questionCourseAnswer);
    }
}
