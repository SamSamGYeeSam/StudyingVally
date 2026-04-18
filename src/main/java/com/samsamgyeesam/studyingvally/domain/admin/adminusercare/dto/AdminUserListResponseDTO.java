package com.samsamgyeesam.studyingvally.domain.admin.adminusercare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/* comment.
 * 관리자의 사용자 관리 목록 화면에 전달할 DTO 클래스
 */

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