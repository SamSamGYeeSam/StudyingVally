package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.repository;

import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.report.entity.AdminReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface AdminReportRepository extends JpaRepository<AdminReport, Long> {


    @Query("""
            select r
            from AdminReport r
            join fetch r.user u
            order by r.reportNo desc
            """)
    List<AdminReport> findAllWithUserOrderByReportNoDesc();

    @Query("""
            select r
            from AdminReport r
            join fetch r.user u
            where r.reportNo = :reportNo
            """)
    Optional<AdminReport> findDetailByReportNo(Long reportNo);
}