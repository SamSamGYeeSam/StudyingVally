package com.samsamgyeesam.studyingvally.domain.course.service;

import com.samsamgyeesam.studyingvally.domain.course.dto.StudentCourseNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.*;
import com.samsamgyeesam.studyingvally.domain.course.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<StudentCourse> getOpenCourses() {
        // 에러 지점 해결: studentEnrollmentRepository를 사용하여 Enrollment 객체를 가져옴
        return studentEnrollmentRepository.findAll().stream()
                .filter(enrollment -> enrollment.getCourse() != null &&
                        "open".equalsIgnoreCase(enrollment.getCourse().getCourseStatus()))
                .map(StudentEnrollment::getCourse)
                .distinct()
                .collect(Collectors.toList());
    }



//    ==================================================================================================

    @Transactional
    public double updateAndGetProgress(Long userNo, Long courseId) {
        long totalChapters = studentChapterRepository.countByCourseId(courseId);
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

        // 에러 지점 해결: Repository를 Enrollment로 변경하여 타입 일치
        StudentCourse course = studentEnrollmentRepository.findByUserNo(userNo).stream()
                .filter(e -> e.getCourse().getCourseId().equals(courseId))
                .map(StudentEnrollment::getCourse)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("강의를 찾을 수 없습니다."));

        List<StudentChapter> chapters = studentChapterRepository.findByCourseId(courseId);
        List<Long> completedChapNos = studentChapterAttemptRepository.findCompletedChapNosByUser(userNo, courseId);
        double progress = updateAndGetProgress(userNo, courseId);

        data.put("course", course);
        data.put("chapters", chapters);
        data.put("completedChaps", completedChapNos);
        data.put("progress", progress);

        return data;
    }

    @Transactional
    public void saveStudentEvaluation(Long userNo, Long courseId, int rating, String content) {
        StudentEvaluation studentEvaluation = StudentEvaluation.builder()
                .userNo(userNo)
                .courseId(courseId)
                .evaluationScore((double) rating)
                .evaluationDesc(content)
                .build();

        studentEvaluationRepository.save(studentEvaluation);
    }

    @Transactional
    public List<Map<String, Object>> getStudentCourseStatus(Long userNo) {
        List<StudentEnrollment> enrollments = studentEnrollmentRepository.findByUserNo(userNo);

        return enrollments.stream().map(enrollment -> {
            Map<String, Object> map = new HashMap<>();

            StudentCourse course = enrollment.getCourse();

            map.put("courseId", (course != null) ? course.getCourseId() : null);
            map.put("courseName", (course != null) ? course.getCourseTitle() : "알 수 없는 강의");

            String professorName = (course != null && course.getUser() != null)
                    ? course.getUser().getUserNickname() : "미지정";
            map.put("professorName", professorName);

            map.put("startDate", "2023-01-01");
            map.put("progress", enrollment.getEnrollmentProcess());

            map.put("quizYn", enrollment.getEnrollmentProcess() > 0 ? "Y" : "N");
            map.put("score", enrollment.getEnrollmentProcess() >= 100 ? "A+" : "진행중");

            return map;
        }).collect(Collectors.toList());
    }

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
}

