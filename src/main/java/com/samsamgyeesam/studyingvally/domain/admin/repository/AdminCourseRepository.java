package com.samsamgyeesam.studyingvally.domain.admin.repository;

import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* comment.
 * 관리자 사용자 상세 화면에서 강의 정보를 조회하기 위한 Repository 인터페이스
 */

public interface AdminCourseRepository extends JpaRepository<AdminCourse, Long> {

    List<AdminCourse> findByUserNoOrderByCourseIdDesc(Long userNo);

    List<AdminCourse> findByCourseIdInOrderByCourseIdDesc(List<Long> courseIds);
}