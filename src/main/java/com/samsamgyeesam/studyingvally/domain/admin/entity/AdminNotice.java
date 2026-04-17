package com.samsamgyeesam.studyingvally.domain.admin.entity;

import com.samsamgyeesam.studyingvally.baseentity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자 공지사항 엔티티 클래스
 *
 * 왜 필요한가:
 * - notice 테이블과 1:1로 매핑하기 위해 필요하다.
 * - 공지사항 등록/수정/조회 시 JPA가 이 엔티티를 기준으로 동작한다.
 *
 * 주의할 점:
 * - notice_no는 DB에서 AUTO_INCREMENT 이므로 @GeneratedValue가 반드시 필요하다.
 * - setter를 무분별하게 사용하지 않고, 엔티티 내부 메서드로 상태를 변경한다.
 */
@Entity
@Table(name = "notice")
@Getter
@NoArgsConstructor
public class AdminNotice extends BaseTimeEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "notice_no")
    private Long noticeNo;

    @Column(name = "notice_title", nullable = false)
    private String noticeTitle;

    @Column(name = "notice_desc", nullable = false, columnDefinition = "TEXT")
    private String noticeDesc;




    public static AdminNotice createNotice(String noticeTitle, String noticeDesc) {
        AdminNotice adminNotice = new AdminNotice();
        adminNotice.noticeTitle = noticeTitle;
        adminNotice.noticeDesc = noticeDesc;
        return adminNotice;
    }


    public void updateNotice(String noticeTitle, String noticeDesc) {
        this.noticeTitle = noticeTitle;
        this.noticeDesc = noticeDesc;
    }
}