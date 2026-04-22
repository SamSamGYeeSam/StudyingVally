package com.samsamgyeesam.studyingvally.domain.npc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReportDTO {
    // 1. 등록 폼에서 받을 데이터
    private String reportTitle;
    private String reportDesc;

    // 2. 조회 시 뷰에 전달할 데이터
    private Long reportNo;
    private String reportStatus;
    private String reportAnswer;
}