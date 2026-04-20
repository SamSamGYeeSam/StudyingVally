package com.samsamgyeesam.studyingvally.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// admin 테이블과 매핑되는 엔티티이다.
@Getter
@Entity
@Table(name = "admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAdmin {

    // 관리자 PK
    @Id
    @Column(name = "admin_no")
    private Long adminNo;

    // 관리자 로그인 아이디
    @Column(name = "admin_id", nullable = false, unique = true)
    private String adminId;

    // 관리자 비밀번호
    @Column(name = "admin_password", nullable = false)
    private String adminPassword;
}