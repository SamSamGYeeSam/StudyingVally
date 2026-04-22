package com.samsamgyeesam.studyingvally.domain.study.service;

import com.samsamgyeesam.studyingvally.domain.study.dto.StudentCourseNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.study.entity.*;
import com.samsamgyeesam.studyingvally.domain.study.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final StudentChapterRepository studentChapterRepository;
    private final StudentChapterAttemptRepository studentChapterAttemptRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final StudentEvaluationRepository studentEvaluationRepository;
    private final StudentCourseQuestionRepository studentCourseQuestionRepository;
    private final StudentUserRepository studentUserRepository;

    public List<StudentCourse> getOpenCourses() {
        return studentCourseRepository.findByCourseStatusIgnoreCaseOrderByCourseCreatedAtDesc("OPEN");
    }


//    public List<StudentChapter> getChapters(Long courseId) {
//        return studentChapterRepository.findByCourse_CourseId(courseId);
//    }

//    ==================================================================================================

    @Transactional
    public double updateAndGetProgress(Long userNo, Long courseId) {
        long totalChapters = studentChapterRepository.countByCourse_CourseId(courseId);
        if (totalChapters == 0) return 0.0;

        long completedChapters = studentChapterAttemptRepository.countCompletedChapters(userNo, courseId);

        double progress = Math.round(((double) completedChapters / totalChapters * 100) * 100) / 100.0;

        studentEnrollmentRepository.updateProgress(userNo, courseId, progress);

        return progress;
    }


    @Transactional
    public void completeChapter(Long userNo, Long chapNo, Long courseId) {
        if (!studentChapterAttemptRepository.existsByUserNoAndChapNo(userNo, chapNo)) {
            StudentChapterAttempt attempt = new StudentChapterAttempt();
            attempt.setUserNo(userNo);
            attempt.setChapNo(chapNo);
            studentChapterAttemptRepository.save(attempt);

            updateAndGetProgress(userNo, courseId);
        }
    }

    public Map<String, Object> getCourseRoomData(Long userNo, Long courseId) {
        Map<String, Object> data = new HashMap<>();

        StudentCourse course = studentEnrollmentRepository.findByUserNo(userNo).stream()
                .filter(e -> e.getCourse().getCourseId().equals(courseId))
                .map(StudentEnrollment::getCourse)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("강의를 찾을 수 없습니다."));

        List<StudentChapter> chapters = studentChapterRepository.findByCourse_CourseId(courseId);
        List<Long> completedChapNos = studentChapterAttemptRepository.findCompletedChapNosByUser(userNo, courseId);
        double progress = updateAndGetProgress(userNo, courseId);

        List<StudentCourseQuestion> myQuestionsInThisCourse =
                studentCourseQuestionRepository.findByUserNoAndCourseIdOrderByQuestionCourseNoDesc(userNo, courseId);

        data.put("course", course);
        data.put("chapters", chapters);
        data.put("completedChaps", completedChapNos);
        data.put("progress", progress);
        data.put("questions", myQuestionsInThisCourse); // 이제 내 질문만 담겨서 나갑니다.

        return data;
    }


//    @Transactional
//    public List<Map<String, Object>> getStudentCourseStatus(Long userNo) {
//        List<StudentEnrollment> enrollments = studentEnrollmentRepository.findByUserNo(userNo);
//
//        return enrollments.stream().map(enrollment -> {
//            Map<String, Object> map = new HashMap<>();
//
//            StudentCourse course = enrollment.getCourse();
//
//            map.put("courseId", (course != null) ? course.getCourseId() : null);
//            map.put("courseName", (course != null) ? course.getCourseTitle() : "알 수 없는 강의");
//
//            String professorName = (course != null && course.getUser() != null)
//                    ? course.getUser().getUserNickname() : "미지정";
//            map.put("professorName", professorName);
//
//            map.put("startDate", "2023-01-01");
//            map.put("progress", enrollment.getEnrollmentProcess());
//
//            map.put("quizYn", enrollment.getEnrollmentProcess() > 0 ? "Y" : "N");
//            map.put("score", enrollment.getEnrollmentProcess() >= 100 ? "A+" : "진행중");
//
//            return map;
//        }).collect(Collectors.toList());
//    }

    public List<StudentCourseNoticeDTO> getCourseNoticesForStudent(Long userNo) {
        List<StudentEnrollment> enrollments = studentEnrollmentRepository.findByUserNo(userNo);
        List<Long> courseIds = enrollments.stream()
                .map(e -> e.getCourse().getCourseId())
                .collect(Collectors.toList());

        if (courseIds.isEmpty()) {
            return new ArrayList<>();
        }

        return studentCourseRepository.findNoticesByCourseIds(courseIds);
    }

    public String getInstructorNickname(Long courseId){
        return studentCourseRepository.findById(courseId)
                .map(course -> {
                    if (course.getUser() != null) {
                        return course.getUser().getUserNickname();
                    }
                    return "선생님";
                })
                .orElse("알 수 없는 선생님");
    }

    @Transactional
    public void saveQuestion(Long userNo, Long courseId, String title, String desc) {
        StudentCourseQuestion question = new StudentCourseQuestion();

        question.setUserNo(userNo);
        question.setCourseId(courseId);
        question.setQuestionCourseTitle(title);
        question.setQuestionCourseDesc(desc);
        question.setCreatedDate(LocalDateTime.now());

        studentCourseQuestionRepository.save(question);
    }

    public List<StudentCourseQuestion> getQuestionsByCourse(Long userNo, Long courseId) {
        return studentCourseQuestionRepository.findByUserNoAndCourseIdOrderByQuestionCourseNoDesc(userNo, courseId);
    }

    public String getInstructorGender(Long courseId) {
        return studentUserRepository.findInstructorGenderByCourseId(courseId);
    }

}

