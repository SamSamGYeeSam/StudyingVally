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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherCourseNoticeService {

    private final TeacherCourseNoticeRepository teacherCourseNoticeRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;


    public List<TeacherCourseNoticeDTO> findCourseNoticeByUserNo(Long userNo) {
        List<TeacherCourseNotice> courseNoticeList = teacherCourseNoticeRepository.findByUserNoOrderByCourseNoticeNoDesc(userNo);

        return courseNoticeList.stream()
                .map(notice -> {
                    TeacherCourseNoticeDTO dto = modelMapper.map(notice, TeacherCourseNoticeDTO.class);

                    // 강의 제목 가져오기
                    if (notice.getCourseId() != null) {
                        Course course = courseRepository.findById(notice.getCourseId()).orElse(null);
                        if (course != null) {
                            dto.setCourseTitle(course.getCourseTitle());
                        }
                    }

                    return dto;
                })
                .collect(Collectors.toList());

    }
}
