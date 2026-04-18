package com.samsamgyeesam.studyingvally.domain.study.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "notice")
public class StudentAdminNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_no")
    private Long noticeNo;

    @Column(name = "notice_title")
    private String noticeTitle;

    @Column(name = "notice_desc")
    private String noticeDesc;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}