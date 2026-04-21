package com.samsamgyeesam.studyingvally.domain.admin.adminusercare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;



@Getter
@AllArgsConstructor
public class AdminUserDetailResponseDTO {

    private Long userNo;
    private String userName;
    private String userNickname;
    private String userPhoneNumber;
    private String userGender;

    private String userRole;
    private String userStatus;

    private String userRoleLabel;
    private String userStatusLabel;

    private String courseSectionTitle;
    private List<String> courseTitleList;

    private long inquiryCount;
    private int reportCount;
}