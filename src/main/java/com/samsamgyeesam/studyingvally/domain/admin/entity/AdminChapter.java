package com.samsamgyeesam.studyingvally.domain.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* comment.
 * 관리자 강의 상세 조회에서 챕터 정보를 조회하기 위한 엔티티 클래스
 */

@Entity
@Table(name = "chapter")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminChapter {

    @Id
    @Column(name = "chap_no")
    private Long chapNo;

    @Column(name = "chap_title", nullable = false)
    private String chapTitle;

    @Column(name = "chap_desc", nullable = false)
    private String chapDesc;

    @Column(name = "chap_url", nullable = false)
    private String chapUrl;

    @Column(name = "course_id")
    private Long courseId;
}