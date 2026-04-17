package com.samsamgyeesam.studyingvally.domain.npc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report")
public class UserReport extends UserNpcQuestionTech{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_no")
    private Long reportNo;

    @Column(name = "report_title")
    private String reportTitle;

    @Column(name = "report_desc", columnDefinition = "TEXT")
    private String reportDesc;

    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "report_status")
    private String reportStatus; // 예: "처리 대기", "처리 완료"

    @Column(name = "report_answer", columnDefinition = "TEXT")
    private String reportAnswer; // 관리자 처리 결과
}