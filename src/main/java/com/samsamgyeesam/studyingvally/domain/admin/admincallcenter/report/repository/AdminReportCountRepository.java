package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.repository;

import com.samsamgyeesam.studyingvally.domain.admin.adminusercare.entity.AdminReportCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AdminReportCountRepository extends JpaRepository<AdminReportCount, Long> {

    Optional<AdminReportCount> findByUserNo(Long userNo);
}