package com.samsamgyeesam.studyingvally.domain.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notice")
@Getter
@NoArgsConstructor
public class AdminNotice {

    @Id
    @Column(name = "notice_no")
    private Long noticeNo;

    @Column(name = "notice_title", nullable = false)
    private String noticeTitle;

    @Column(name = "notice_desc", nullable = false)
    private String noticeDesc;
}