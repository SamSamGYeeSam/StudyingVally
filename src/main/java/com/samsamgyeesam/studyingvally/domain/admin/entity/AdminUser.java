package com.samsamgyeesam.studyingvally.domain.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* comment.
 * 관리자 사용자 관리 기능에서 user 테이블을 조회하기 위한 전용 엔티티 클래스
 */

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminUser {

    @Id
    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_phone_number", nullable = false)
    private String userPhoneNumber;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_role", nullable = false)
    private String userRole;

    @Column(name = "user_nickname", nullable = false)
    private String userNickname;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_status", nullable = false)
    private String userStatus;

    @Column(name = "user_gender", nullable = false)
    private String userGender;

    public void changeUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}