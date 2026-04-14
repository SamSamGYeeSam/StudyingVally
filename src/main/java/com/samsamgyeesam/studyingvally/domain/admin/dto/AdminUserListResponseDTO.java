package com.samsamgyeesam.studyingvally.domain.admin.dto;

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

    /* 원본 값 */
    private String userRole;
    private String userStatus;

    /* 화면 출력용 한글 값 */
    private String userRoleLabel;
    private String userStatusLabel;
}