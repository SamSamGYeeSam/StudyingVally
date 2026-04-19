package com.samsamgyeesam.studyingvally.domain.study.service;

import com.samsamgyeesam.studyingvally.domain.study.dto.StudentAdminNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.study.dto.StudentCourseNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentEnrollment;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentEnrollmentRepository;
import com.samsamgyeesam.studyingvally.domain.study.dto.StudentDTO;
import com.samsamgyeesam.studyingvally.domain.study.entity.StudentUser;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentEvaluationRepository;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentNoticeRepository;
import com.samsamgyeesam.studyingvally.domain.study.repository.StudentUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {

    private final StudentUserRepository studentUserRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final StudentNoticeRepository studentnoticeRepository;
    private final StudentEvaluationRepository studentEvaluationRepository;

    public Long findUserNoByUserId(String userId) {
        return studentUserRepository.findByUserId(userId)
                .map(StudentUser::getUserNo)
                .orElseThrow(() -> new RuntimeException("해당 아이디를 가진 사용자를 찾을 수 없습니다: " + userId));
    }

    public StudentDTO getStudentMainData(Long userNo) throws IllegalAccessException {
        StudentUser user = studentUserRepository.findById(userNo)
                .orElseThrow(() -> new IllegalAccessException("존재하지 않는 사용자입니다."));

        List<StudentEnrollment> studentEnrollments = studentEnrollmentRepository.findByUserNo(userNo);
        List<StudentDTO.EnrolledCourseDTO> courseList = studentEnrollments.stream()
                .map(en -> {
                    boolean exists = studentEvaluationRepository.existsByUser_UserNoAndStudentCourse_CourseId(
                            userNo, en.getCourse().getCourseId()
                    );

                    return StudentDTO.EnrolledCourseDTO.builder()
                            .courseId(en.getCourse().getCourseId())
                            .courseTitle(en.getCourse().getCourseTitle())
                            .progress(en.getEnrollmentProcess().intValue())
                            .hasEvaluation(exists) // 이제 정상적으로 적용됩니다.
                            .targetUrl("/student/course/" + en.getCourse().getCourseId())
                            .build();
                })
                .collect(Collectors.toList());

        return StudentDTO.builder()
                .userNo(user.getUserNo())
                .userId(user.getUserId())
                .userEmail(user.getUserEmail())
                .userNickname(user.getUserNickname())
                .userName(user.getUserName())
                .userRole(user.getUserRole())
                .userPhoneNumber(user.getUserPhoneNumber())
                .userStatus(user.getUserStatus())
                .userGender(user.getUserGender())
                .enrolledCourses(courseList)
                .build();
    }
    public List<StudentCourseNoticeDTO> getCourseNotices(Long userNo) {
        List<Long> courseIds = studentEnrollmentRepository.findByUserNo(userNo)
                .stream()
                .map(e -> e.getCourse().getCourseId())
                .collect(Collectors.toList());

        if (courseIds.isEmpty()) return new ArrayList<>();

        return studentnoticeRepository.findMyCourseNotices(courseIds);
    }

    public List<StudentAdminNoticeDTO> getAdminNotices() {
        return studentnoticeRepository.findAllAdminNotices();
    }

}