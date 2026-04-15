package com.samsamgyeesam.studyingvally.domain.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chapter")
@Getter
@Setter
public class StudentChapter {

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

}
