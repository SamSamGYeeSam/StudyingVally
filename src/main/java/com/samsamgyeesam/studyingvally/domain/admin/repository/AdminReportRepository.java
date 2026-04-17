package com.samsamgyeesam.studyingvally.domain.admin.repository;

import com.samsamgyeesam.studyingvally.domain.admin.entity.AdminReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* comment.
 * 고객센터 신고함 목록 조회용 Repository
 */

public interface AdminReportRepository extends JpaRepository<AdminReport, Long> {

    List<AdminReport> findAllByOrderByReportNoDesc();
}