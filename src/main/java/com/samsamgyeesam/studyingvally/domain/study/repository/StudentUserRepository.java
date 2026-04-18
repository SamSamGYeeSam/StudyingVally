package com.samsamgyeesam.studyingvally.domain.study.repository;

import com.samsamgyeesam.studyingvally.domain.study.entity.StudentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentUserRepository extends JpaRepository<StudentUser, Long> {
//    Optional<Object> findById(Long userNo);
    Optional<StudentUser> findByUserId(String userId);
//    public Optional<Object> findById(Long userNo) {
//    }
}
