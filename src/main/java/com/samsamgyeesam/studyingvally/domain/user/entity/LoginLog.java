package com.samsamgyeesam.studyingvally.domain.user.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * 로그인 성공/실패 로그를 저장하는 엔티티
 */
@Entity
@Table(name = "login_log")
public class LoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "login_time", nullable = false)
    private LocalDateTime loginTime;

    @Column(name = "success", nullable = false)
    private boolean success;

    @Column(name = "ip_address")
    private String ipAddress;

    public LoginLog() {
    }

    public LoginLog(String userId, LocalDateTime loginTime, boolean success, String ipAddress) {
        this.userId = userId;
        this.loginTime = loginTime;
        this.success = success;
        this.ipAddress = ipAddress;
    }
}