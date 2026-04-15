package com.samsamgyeesam.studyingvally.domain.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "user")
public class QuizUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Integer userNo;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_phone_number")
    private String userPhoneNumber;

    @Column(name = "user_email")
    private String userEmail;

    // 실제 프로젝트에 Role Enum이 있다면 그 타입으로 변경해주세요. (예: RoleType userRole)
    @Column(name = "user_role")
    private String userRole;

    @Column(name = "user_nickname")
    private String userNickname;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_status")
    private String userStatus;

    @Column(name = "user_gender")
    private String userGender;

}