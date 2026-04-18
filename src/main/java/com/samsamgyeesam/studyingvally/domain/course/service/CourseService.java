package com.samsamgyeesam.studyingvally.domain.course.service;

import com.samsamgyeesam.studyingvally.domain.course.dto.CourseDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.Course;
import com.samsamgyeesam.studyingvally.domain.course.repository.ChapterRepository;
import com.samsamgyeesam.studyingvally.domain.course.repository.CourseRepository;
import com.samsamgyeesam.studyingvally.domain.course.repository.EnrollmentRepository;
import com.samsamgyeesam.studyingvally.domain.course.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ChapterRepository chapterRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EvaluationRepository evaluationRepository;
    private final ModelMapper modelMapper;

    // 강사 본인이 올린 강의 전체 조회 - 강사 번호(사용자 번호)로 조회 - list로 여러 개 반환
    public List<CourseDTO> findAllCoursesByUserNo(Long userNo) {
        List<Course> courseList = courseRepository.findByUserNoOrderByCourseCreatedAtDesc(userNo);

        return courseList.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }

    // 수정하기 버튼 눌럿을 때 그 강의를 먼저 찾기 위함 - dto 객체로 조회 결과 하나 반환
    public CourseDTO findCourseById(Long courseId) {
        Course foundCourse = courseRepository.findById(courseId)
                .orElseThrow(IllegalArgumentException::new);

        return modelMapper.map(foundCourse, CourseDTO.class);
    }

    // 실제 강의 수정
    @Transactional
    public void updateCourse(Long courseId, String courseTitle, String courseDescription) {

        // 강의 조회
        Course foundCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다."));

        foundCourse.updateCourseInfo(courseTitle,courseDescription);

    }

    // 강의 삭제
    @Transactional
    public void deleteCourse(Long courseId) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다."));

        chapterRepository.deleteByCourseId(courseId);
        enrollmentRepository.deleteByCourseId(courseId);
        evaluationRepository.deleteByCourseId(courseId);
        courseRepository.deleteById(courseId);
    }

    // 강의 등록
    @Transactional
    public Long registCourse(CourseDTO courseDTO) {
        Course course = new Course(
                courseDTO.getCourseTitle(),
                courseDTO.getCourseDescription(),
                "CLOSED",  // 미개설
                0,         // 승인 요청 하기 전
                courseDTO.getUserNo()
        );

        Course savedCourse = courseRepository.save(course);

        return savedCourse.getCourseId();
    }

}
