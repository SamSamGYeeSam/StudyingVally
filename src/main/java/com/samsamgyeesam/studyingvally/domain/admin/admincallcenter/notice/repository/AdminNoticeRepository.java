package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.repository;

import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.entity.AdminNotice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminNoticeRepository extends JpaRepository<AdminNotice, Long> {

    List<AdminNotice> findAllByOrderByNoticeNoDesc();
}