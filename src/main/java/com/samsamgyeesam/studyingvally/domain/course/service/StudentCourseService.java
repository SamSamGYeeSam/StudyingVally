package com.samsamgyeesam.studyingvally.domain.course.service;

import com.samsamgyeesam.studyingvally.domain.course.entity.StudentCourse;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> getOpenCourses() {
        return studentCourseRepository.findAll().stream()
                .filter(course -> "open".equalsIgnoreCase(course.getCourseStatus()))
                .collect(Collectors.toList());
    }
}
