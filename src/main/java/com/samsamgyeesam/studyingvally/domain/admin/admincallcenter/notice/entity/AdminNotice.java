package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.entity;

import com.samsamgyeesam.studyingvally.baseentity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

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