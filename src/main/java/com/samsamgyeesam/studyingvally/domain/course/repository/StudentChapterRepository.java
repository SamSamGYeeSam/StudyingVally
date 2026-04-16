package com.samsamgyeesam.studyingvally.domain.course.repository;

import com.samsamgyeesam.studyingvally.domain.course.entity.StudentChapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentChapterRepository extends JpaRepository<StudentChapter, Long> {

    List<StudentChapter> findByCourseId(Long courseId);
    long countByCourseId(Long courseId);

}
