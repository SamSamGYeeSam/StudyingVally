package com.samsamgyeesam.studyingvally.domain.user.service;

import com.samsamgyeesam.studyingvally.domain.admin.dto.StudentAdminNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.course.dto.StudentCourseNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.StudentCourseNotice;
import com.samsamgyeesam.studyingvally.domain.course.entity.StudentEnrollment;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentEnrollmentRepository;
import com.samsamgyeesam.studyingvally.domain.user.dto.StudentDTO;
import com.samsamgyeesam.studyingvally.domain.user.entity.StudentNotice;
import com.samsamgyeesam.studyingvally.domain.user.entity.StudentUser;
import com.samsamgyeesam.studyingvally.domain.user.repository.StudentNoticeRepository;
import com.samsamgyeesam.studyingvally.domain.user.repository.StudentUserRepository;
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


    public StudentDTO getStudentMainData(Long userNo) throws IllegalAccessException {
        StudentUser user = studentUserRepository.findById(userNo)
                .orElseThrow(() -> new IllegalAccessException("존재하지 않는 사용자입니다."));

        List<StudentEnrollment> studentEnrollments = studentEnrollmentRepository.findByUserNo(userNo);

        List<StudentDTO.EnrolledCourseDTO> courseList = studentEnrollments.stream()
                .map(en -> StudentDTO.EnrolledCourseDTO.builder()
                        .courseId(en.getCourse().getCourseId())
                        .courseTitle(en.getCourse().getCourseTitle())
                        .progress(en.getEnrollmentProcess().intValue()) // 진도율
                        .targetUrl("/student/course/" + en.getCourse().getCourseId())
                        .build())
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

        // 61라인 해결: 리포지토리에 추가한 메서드명을 정확히 호출
        return studentnoticeRepository.findMyCourseNotices(courseIds);
    }

    public List<StudentAdminNoticeDTO> getAdminNotices() {
        return studentnoticeRepository.findAllAdminNotices();
    }


}



