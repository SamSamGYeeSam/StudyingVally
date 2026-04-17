package com.samsamgyeesam.studyingvally.domain.npc.repository;

import com.samsamgyeesam.studyingvally.domain.npc.entity.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Long> {
    // 내 신고 내역을 최신순으로 가져오기
    List<UserReport> findByUserNoOrderByReportNoDesc(Long userNo);
}