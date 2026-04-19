package com.samsamgyeesam.studyingvally.domain.notice.service;


import com.samsamgyeesam.studyingvally.domain.course.entity.Course;
import com.samsamgyeesam.studyingvally.domain.course.repository.CourseRepository;
import com.samsamgyeesam.studyingvally.domain.notice.dto.TeacherCourseNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.notice.dto.TeacherNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.notice.entity.TeacherCourseNotice;
import com.samsamgyeesam.studyingvally.domain.notice.repository.TeacherCourseNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherCourseNoticeService {

    private final TeacherCourseNoticeRepository teacherCourseNoticeRepository;
    private final ModelMapper modelMapper;

    // 강의소식 전체 조회
    public List<TeacherCourseNoticeDTO> findCourseNoticeByUserNo(Long userNo) {
        List<TeacherCourseNotice> courseNoticeList = teacherCourseNoticeRepository.findByUserNoWithCourse(userNo);

        return courseNoticeList.stream()
                .map(notice -> {
                    TeacherCourseNoticeDTO teacherCourseNoticeDTO = modelMapper.map(notice, TeacherCourseNoticeDTO.class);

                    // 강의 제목 가져오기
                    if (notice.getCourse() != null) {
                        teacherCourseNoticeDTO.setCourseName(notice.getCourse().getCourseTitle());
                    }

                    return teacherCourseNoticeDTO;
                })
                .collect(Collectors.toList());
    }


    // 강의소식 상세 조회
    public TeacherCourseNoticeDTO findCourseNoticeById(Long courseNoticeNo) {
        TeacherCourseNotice courseNotice = teacherCourseNoticeRepository.findById(courseNoticeNo)
                .orElseThrow(() -> new IllegalArgumentException("강의소식을 찾을 수 없습니다."));

        TeacherCourseNoticeDTO teacherCourseNoticeDTO = modelMapper.map(courseNotice, TeacherCourseNoticeDTO.class);

        // 강의 제목 가져오기
        if (courseNotice.getCourse() != null) {
            teacherCourseNoticeDTO.setCourseName(courseNotice.getCourse().getCourseTitle());
        }

        return teacherCourseNoticeDTO;
    }

    // 강의소식 등록
    @Transactional
    public void registCourseNotice(TeacherCourseNoticeDTO courseNoticeDTO) {
        TeacherCourseNotice courseNotice = new TeacherCourseNotice(
                courseNoticeDTO.getCourseNoticeTitle(),
                courseNoticeDTO.getCourseNoticeDesc(),
                courseNoticeDTO.getCourseId()
        );

        teacherCourseNoticeRepository.save(courseNotice);
    }

    // 강의소식 삭제
    @Transactional
    public void deleteCourseNotice(Long courseNoticeNo) {

        teacherCourseNoticeRepository.deleteById(courseNoticeNo);
    }

    // 강의소식 수정
    @Transactional
    public void updateCourseNotice(Long courseNoticeNo, String courseNoticeTitle, String courseNoticeDesc) {

        TeacherCourseNotice foundCourseNotice = teacherCourseNoticeRepository.findById(courseNoticeNo)
                .orElseThrow(() -> new IllegalArgumentException("강의소식을 찾을 수 없습니다."));

        foundCourseNotice.updateCourseNoticeInfo(courseNoticeTitle, courseNoticeDesc);
    }
}
