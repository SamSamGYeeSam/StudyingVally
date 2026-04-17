package com.samsamgyeesam.studyingvally.domain.npc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNpcQuestionTechDTO {

    // 1. 폼에서 입력받을 때 사용하는 필드
    private String questionTitle;
    private String questionDesc;

    // 2. 리스트 조회 후 화면에 출력할 때 필요한 필드 (추가됨)
    private Long questionTechNo;
    private String questionStatus;
    private String questionAnswer;
}