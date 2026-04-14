package com.samsamgyeesam.studyingvally.domain.admin.repository;

import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminReportCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/* comment.
 * 관리자 사용자 상세 화면에서 신고 수를 조회하기 위한 Repository 인터페이스
 */

public interface AdminReportCountRepository extends JpaRepository<AdminReportCount, Long> {

    Optional<AdminReportCount> findByUserNo(Long userNo);
}