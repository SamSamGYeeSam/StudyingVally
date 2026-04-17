package com.samsamgyeesam.studyingvally.domain.npc.entity;

import com.samsamgyeesam.studyingvally.baseentity.BaseOnlyCreateTime;
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
@Table(name = "question_tech")
public class UserNpcQuestionTech extends BaseOnlyCreateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_tech_no")
    private Long questionTechNo;

    @Column(name = "question_title")
    private String questionTitle;

    @Column(name = "question_desc", columnDefinition = "TEXT")
    private String questionDesc;

    @Column(name = "user_no")
    private Long userNo;

    // [신규 추가] 답변 상태 (예: "답변 대기", "답변 완료")
    @Column(name = "question_status")
    private String questionStatus;

    // [유지] 관리자가 달아줄 답변
    @Column(name = "question_answer", columnDefinition = "TEXT")
    private String questionAnswer;
}