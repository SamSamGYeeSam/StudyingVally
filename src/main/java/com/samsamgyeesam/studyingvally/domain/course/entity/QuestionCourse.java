package com.samsamgyeesam.studyingvally.domain.course.entity;

import com.samsamgyeesam.studyingvally.baseentity.BaseTimeEntity;
import com.samsamgyeesam.studyingvally.domain.user.entity.UserUser;
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
@Table(name = "question_course")
public class QuestionCourse extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_course_no")
    private Long questionCourseNo;

    @Column(name = "question_course_title", nullable = false)
    private String questionCourseTitle;

    @Column(name = "question_course_desc", nullable = false)
    private String questionCourseDesc;

    @Column(name = "user_no", nullable = false)
    private Long userNo;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "question_course_answer")
    private String questionCourseAnswer;

    // 강의랑 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;

    // 수강생이랑 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", insertable = false, updatable = false)
    private UserUser user;

    // 답변 등록 처리
    public void answerQuestion(String questionCourseAnswer) {
        this.questionCourseAnswer = questionCourseAnswer;
    }
}
