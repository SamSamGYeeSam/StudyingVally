package com.samsamgyeesam.studyingvally.domain.study.repository;

import com.samsamgyeesam.studyingvally.domain.study.entity.StudentChapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentChapterRepository extends JpaRepository<StudentChapter, Long> {

    List<StudentChapter> findByCourseId(Long courseId);
    long countByCourseId(Long courseId);

}
