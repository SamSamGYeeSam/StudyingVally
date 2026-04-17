package com.samsamgyeesam.studyingvally.domain.admin.dto.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 공지사항 등록 요청 DTO
 *
 * 왜 필요한가:
 * - 등록 폼에서 전달된 제목/내용 데이터를 받기 위함이다.
 * - 화면 입력값과 엔티티를 직접 연결하지 않고 중간 계층을 둔다.
 */
@Getter
@Setter
@NoArgsConstructor
public class AdminNoticeRegistRequestDTO {

    private String noticeTitle;
    private String noticeDesc;
}