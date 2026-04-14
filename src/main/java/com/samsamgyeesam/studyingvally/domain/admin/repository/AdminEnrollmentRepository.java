package com.samsamgyeesam.studyingvally.domain.admin.repository;

import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* comment.
 * 관리자 사용자 상세 화면에서 수강 정보를 조회하기 위한 Repository 인터페이스
 */

public interface AdminEnrollmentRepository extends JpaRepository<AdminEnrollment, Long> {

    List<AdminEnrollment> findByUserNoOrderByEnrollmentNoDesc(Long userNo);
}