package com.samsamgyeesam.studyingvally.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/* comment.
 * 관리자 사용자 상세 화면에 전달할 DTO 클래스
 */

@Getter
@AllArgsConstructor
public class AdminUserDetailResponseDTO {

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

    /* 역할별 강의 목록 제목 */
    private String courseSectionTitle;

    /* 역할별 강의 제목 목록 */
    private List<String> courseTitleList;

    /* 문의 수 */
    private long inquiryCount;

    /* 신고 수 */
    private int reportCount;
}