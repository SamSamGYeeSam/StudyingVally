package com.samsamgyeesam.studyingvally.domain.course.service;

import com.samsamgyeesam.studyingvally.domain.course.entity.StudentChapter;
import com.samsamgyeesam.studyingvally.domain.course.entity.StudentChapterAttempt;
import com.samsamgyeesam.studyingvally.domain.course.entity.StudentCourse;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentChapterAttemptRepository;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentChapterRepository;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentCourseRepository;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentEnrollmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<StudentCourse> getOpenCourses() {
        return studentCourseRepository.findAll().stream()
                .filter(course -> "open".equalsIgnoreCase(course.getCourseStatus()))
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

        // 1. 코스 기본 정보
        StudentCourse course = studentCourseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("강의를 찾을 수 없습니다."));

        // 2. 전체 챕터 목록 조회 (이미지 2의 리스트 출력용)
        List<StudentChapter> chapters = studentChapterRepository.findByCourseId(courseId);

        // 3. 현재 유저의 챕터 완료 상태 목록 (어떤 챕터를 완료했는지 체크용)
        List<Long> completedChapNos = studentChapterAttemptRepository.findCompletedChapNosByUser(userNo, courseId);

        // 4. 진행률 (이미 만들어두신 메서드 활용)
        double progress = updateAndGetProgress(userNo, courseId);

        data.put("course", course);
        data.put("chapters", chapters);
        data.put("completedChaps", completedChapNos);
        data.put("progress", progress);

        return data;
    }

}
