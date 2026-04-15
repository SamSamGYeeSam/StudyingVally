package com.samsamgyeesam.studyingvally.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/* comment.
 * 관리자 강의 상세 화면의 챕터 정보를 전달할 DTO 클래스
 */

@Getter
@AllArgsConstructor
public class AdminChapterResponseDTO {

    private Long chapNo;
    private String chapTitle;
    private String chapDesc;
    private String chapUrl;
}