package com.samsamgyeesam.studyingvally.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "user")
public class StudentUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no") // SQL의 user_no 컬럼
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

}
