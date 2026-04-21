package com.samsamgyeesam.studyingvally.domain.admin.adminusercare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public class AdminUserListResponseDTO {

    private Long userNo;
    private String userName;
    private String userNickname;
    private String userPhoneNumber;
    private String userRole;
    private String userStatus;
    private String userRoleLabel;
    private String userStatusLabel;
}