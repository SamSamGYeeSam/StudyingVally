package com.samsamgyeesam.studyingvally.domain.notice.service;

import com.samsamgyeesam.studyingvally.domain.notice.dto.TeacherNoticeDTO;
import com.samsamgyeesam.studyingvally.domain.notice.entity.TeacherNotice;
import com.samsamgyeesam.studyingvally.domain.notice.repository.TeacherNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherNoticeService {

    private final TeacherNoticeRepository teacherNoticeRepository;
    private final ModelMapper modelMapper;

    // 전체 공지사항 조회
    public List<TeacherNoticeDTO> findAllNotices() {
        List<TeacherNotice> noticeList = teacherNoticeRepository.findAllByOrderByNoticeNoDesc();

        return noticeList.stream()
                .map(notice -> modelMapper.map(notice, TeacherNoticeDTO.class))
                .collect(Collectors.toList());
    }

}
