package com.samsamgyeesam.studyingvally.domain.user.repository;

import com.samsamgyeesam.studyingvally.domain.user.entity.StudentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentUserRepository extends JpaRepository<StudentUser, Long> {
//    Optional<Object> findById(Long userNo);

//    public Optional<Object> findById(Long userNo) {
//    }
}
