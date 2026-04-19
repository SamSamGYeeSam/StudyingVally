package com.samsamgyeesam.studyingvally.domain.notice.service;

import com.samsamgyeesam.studyingvally.domain.course.exception.CourseException;
import com.samsamgyeesam.studyingvally.domain.notice.dto.TeacherCourseNoticeDTO;
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
                .orElseThrow(() -> new CourseException("강의소식을 찾을 수 없습니다."));

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

        if (courseNoticeDTO.getCourseId() == null) {
            throw new CourseException("강의를 선택해주세요.");
        }

        if (courseNoticeDTO.getCourseNoticeTitle() == null || courseNoticeDTO.getCourseNoticeTitle().trim().isEmpty()) {
            throw new CourseException("제목을 입력해주세요.");
        }

        if (courseNoticeDTO.getCourseNoticeDesc() == null || courseNoticeDTO.getCourseNoticeDesc().trim().isEmpty()) {
            throw new CourseException("내용을 입력해주세요.");
        }

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

        if (!teacherCourseNoticeRepository.existsById(courseNoticeNo)) {
            throw new CourseException("삭제할 강의소식이 존재하지 않습니다.");
        }

        teacherCourseNoticeRepository.deleteById(courseNoticeNo);
    }

    // 강의소식 수정
    @Transactional
    public void updateCourseNotice(TeacherCourseNoticeDTO courseNoticeDTO) {

        if (courseNoticeDTO.getCourseNoticeTitle() == null || courseNoticeDTO.getCourseNoticeTitle().trim().isEmpty()) {
            throw new CourseException("강의 소식의 제목을 입력해주세요.");
        }

        if (courseNoticeDTO.getCourseNoticeDesc() == null || courseNoticeDTO.getCourseNoticeDesc().trim().isEmpty()) {
            throw new CourseException("강의 소식의 내용을 입력해주세요.");
        }

        TeacherCourseNotice foundCourseNotice = teacherCourseNoticeRepository.findById(courseNoticeDTO.getCourseNoticeNo())
                .orElseThrow(() -> new CourseException("강의소식을 찾을 수 없습니다."));

        foundCourseNotice.updateCourseNoticeInfo(courseNoticeDTO.getCourseNoticeTitle(), courseNoticeDTO.getCourseNoticeDesc());
    }
}
