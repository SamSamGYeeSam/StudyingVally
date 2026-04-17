package com.samsamgyeesam.studyingvally.domain.admin.dto.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 공지사항 수정 요청 DTO
 *
 * 왜 필요한가:
 * - 수정 폼에서 noticeNo, 제목, 내용을 함께 전달받기 위함이다.
 */
@Getter
@Setter
@NoArgsConstructor
public class AdminNoticeUpdateRequestDTO {

    private Long noticeNo;
    private String noticeTitle;
    private String noticeDesc;
}