package com.samsamgyeesam.studyingvally.domain.quiz.entity;

import com.samsamgyeesam.studyingvally.baseentity.BaseTimeEntity;
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
@Table(name = "chapter") // DB 테이블명은 기존처럼 chapter로 유지했습니다. (필요시 quiz_chapter 등으로 수정)
public class QuizChapter extends BaseTimeEntity {

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