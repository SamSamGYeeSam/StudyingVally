package com.samsamgyeesam.studyingvally.domain.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* comment.
 * 관리자 사용자 상세 조회에서 문의 내역을 조회하기 위한 엔티티 클래스
 */

@Entity
@Table(name = "question_tech")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminQuestionTech {

    @Id
    @Column(name = "question_tech_no")
    private Long questionTechNo;

    @Column(name = "user_no")
    private Long userNo;
}