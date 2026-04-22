package com.samsamgyeesam.studyingvally.domain.course.service;

import com.samsamgyeesam.studyingvally.domain.course.dto.CourseDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.Course;
import com.samsamgyeesam.studyingvally.domain.course.exception.CourseException;
import com.samsamgyeesam.studyingvally.domain.course.repository.*;
import com.samsamgyeesam.studyingvally.domain.notice.repository.TeacherCourseNoticeRepository;
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
    private final QuestionCourseRepository questionCourseRepository;
    private final TeacherCourseNoticeRepository courseNoticeRepository;
    private final ModelMapper modelMapper;

    // 강사 본인이 올린 강의 전체 조회 - 강사 번호(사용자 번호)로 조회 - list로 여러 개 반환
    public List<CourseDTO> findAllCoursesByUserNo(Long userNo) {
        List<Course> courseList = courseRepository.findByUserNoOrderByCourseCreatedAtDesc(userNo);

        return courseList.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }

    // 강의 찾기
    public CourseDTO findCourseById(Long courseId) {
        Course foundCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException("해당 강의를 찾을 수 없습니다."));

        return modelMapper.map(foundCourse, CourseDTO.class);
    }

    // 실제 강의 수정
    @Transactional
    public void updateCourse(CourseDTO courseDTO) {

        if (courseDTO.getCourseTitle() == null || courseDTO.getCourseTitle().trim().isEmpty()) {
            throw new CourseException("강의 제목을 입력해주세요.");
        }
//        if (courseDTO.getCourseTitle().trim().length() < 5) {
//            throw new CourseException("강의 제목은 최소 5글자 이상이어야 합니다.");
//        }
        if (courseDTO.getCourseDescription() == null || courseDTO.getCourseDescription().trim().isEmpty()) {
            throw new CourseException("강의 설명을 입력해주세요.");
        }

        // 강의 조회
        Course foundCourse = courseRepository.findById(courseDTO.getCourseId())
                .orElseThrow(() -> new CourseException("해당 강의가 존재하지 않습니다."));

        foundCourse.updateCourseInfo(courseDTO.getCourseTitle(), courseDTO.getCourseDescription());
    }

    // 강의 삭제
    @Transactional
    public String deleteCourse(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException("강의가 존재하지 않습니다."));

        String title = course.getCourseTitle();

        chapterRepository.deleteByCourseId(courseId);
        enrollmentRepository.deleteByCourseId(courseId);
        evaluationRepository.deleteByCourseId(courseId);
        questionCourseRepository.deleteByCourseId(courseId);
        courseNoticeRepository.deleteByCourseId(courseId);

        courseRepository.delete(course);

        return title;
    }

    // 강의 등록
    @Transactional
    public Long registCourse(CourseDTO courseDTO) {

        if (courseDTO.getCourseTitle() == null || courseDTO.getCourseTitle().trim().isEmpty()) {
            throw new CourseException("강의 제목을 입력해주세요.");
        }
//        if (courseDTO.getCourseTitle().trim().length() < 5) {
//            throw new CourseException("강의 제목은 최소 5글자 이상이어야 합니다.");
//        }
        if (courseDTO.getCourseDescription() == null || courseDTO.getCourseDescription().trim().isEmpty()) {
            throw new CourseException("강의 설명을 입력해주세요.");
        }

        // 동일한 강의명이 이미 있는 경우
        List<Course> existingCourses = courseRepository.findByUserNoOrderByCourseCreatedAtDesc(courseDTO.getUserNo());
        boolean isDuplicate = existingCourses.stream()
                .anyMatch(course -> course.getCourseTitle().equals(courseDTO.getCourseTitle()));

        if (isDuplicate) {
            throw new CourseException("이미 동일한 이름의 강의가 존재합니다.");
        }

        Course course = new Course(
                courseDTO.getCourseTitle(),
                courseDTO.getCourseDescription(),
                "CLOSED",  // 미개설
                1,         // 승인 요청
                courseDTO.getUserNo()
        );

        Course savedCourse = courseRepository.save(course);

        return savedCourse.getCourseId();
    }

}
