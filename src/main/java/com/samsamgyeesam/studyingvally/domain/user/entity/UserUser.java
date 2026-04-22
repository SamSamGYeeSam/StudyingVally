package com.samsamgyeesam.studyingvally.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// user 테이블과 매핑되는 엔티티
@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUser {

    // 사용자 PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;

    // 로그인 아이디
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    // 비밀번호
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    // 전화번호
    @Column(name = "user_phone_number")
    private String userPhoneNumber;

    // 이메일
    @Column(name = "user_email")
    private String userEmail;

    // 사용자 권한
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    // 닉네임
    @Column(name = "user_nickname")
    private String userNickname;

    // 사용자 이름
    @Column(name = "user_name", nullable = false)
    private String userName;

    /*
     * 사용자 상태
     * 예: ACTIVE
     */
    @Column(name = "user_status", nullable = false)
    private String userStatus;

    // 성별
    @Column(name = "user_gender", nullable = false)
    private String userGender;

    // 기존 프로젝트 스타일에 맞춘 builder 시작 메서드
    public static UserUser builder() {
        return new UserUser();
    }

    public UserUser userId(String userId) {
        this.userId = userId;
        return this;
    }

    public UserUser userPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }

    public UserUser userPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
        return this;
    }

    public UserUser userEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public UserUser userRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public UserUser userNickname(String userNickname) {
        this.userNickname = userNickname;
        return this;
    }

    public UserUser userName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserUser userStatus(String userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public UserUser userGender(String userGender) {
        this.userGender = userGender;
        return this;
    }


    // 내 정보 수정
    public void updateInformation(String userNickname,
                                  String userPhoneNumber,
                                  String userEmail,
                                  String userGender,
                                  String userPassword) {
        this.userNickname = userNickname;
        this.userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userPassword = userPassword;
    }

    // 비밀번호 재설정 업데이트
    public void updatePassword(String userPassword) {
        this.userPassword = userPassword;
    }

    // 상태 확인 메서드
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(this.userStatus);
    }
}