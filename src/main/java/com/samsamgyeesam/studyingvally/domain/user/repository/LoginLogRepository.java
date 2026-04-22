package com.samsamgyeesam.studyingvally.domain.user.repository;

import com.samsamgyeesam.studyingvally.domain.user.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

// loginlog 엔티티를 DB에 저장/조회/삭제하기 위한 JPA 저장소
public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {
}