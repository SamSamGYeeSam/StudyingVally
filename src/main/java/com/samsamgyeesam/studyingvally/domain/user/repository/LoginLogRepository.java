package com.samsamgyeesam.studyingvally.domain.user.repository;

import com.samsamgyeesam.studyingvally.domain.user.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {
}