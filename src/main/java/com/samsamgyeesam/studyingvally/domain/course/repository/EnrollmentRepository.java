package com.samsamgyeesam.studyingvally.domain.course.repository;

import com.samsamgyeesam.studyingvally.domain.course.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // 강의 삭제 시 수강생 같이 삭제
    void deleteByCourseId(Long courseId);

    // 수강생 조회
    List<Enrollment> findByCourseIdOrderByEnrollmentProcessDesc(Long courseId);

}
