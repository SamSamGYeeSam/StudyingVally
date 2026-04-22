package com.samsamgyeesam.studyingvally.domain.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "chapter")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chap_no")
    private Long chapNo;

    @Column(name = "chap_title")
    private String chapTitle;

    @Column(name = "chap_desc")
    private String chapDesc;

    @Column(name = "chap_url")
    private String chapUrl;

    @Column(name = "course_id")
    private Long courseId;

    public Chapter(String chapTitle, String chapDesc, String chapUrl, Long courseId) {
        this.chapTitle = chapTitle;
        this.chapDesc = chapDesc;
        this.chapUrl = chapUrl;
        this.courseId = courseId;
    }

    //챕터 수정
    public void updateChapterInfo(String chapTitle, String chapDesc, String chapUrl) {
        this.chapTitle = chapTitle;
        this.chapDesc = chapDesc;
        this.chapUrl = chapUrl;
    }
}