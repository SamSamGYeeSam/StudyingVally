package com.samsamgyeesam.studyingvally.domain.course.repository;

import com.samsamgyeesam.studyingvally.domain.course.entity.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {
    List<StudentEnrollment> findByUserNo(Long userNo);
}


