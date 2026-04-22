package com.samsamgyeesam.studyingvally.domain.user.repository;

import com.samsamgyeesam.studyingvally.domain.user.entity.UserAccountState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountStateRepository extends JpaRepository<UserAccountState, Long> {
}