package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.service;

import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.dto.AdminNoticeDetailDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.dto.AdminNoticeListDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.dto.AdminNoticeRegistRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.dto.AdminNoticeUpdateRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.entity.AdminNotice;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.repository.AdminNoticeRepository;
import com.samsamgyeesam.studyingvally.domain.admin.exception.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminNoticeService {

    private final AdminNoticeRepository adminNoticeRepository;

    public List<AdminNoticeListDTO> findAllNoticeList() {
        return adminNoticeRepository.findAllByOrderByNoticeNoDesc()
                .stream()
                .map(notice -> new AdminNoticeListDTO(
                        notice.getNoticeNo(),
                        notice.getNoticeTitle(),
                        notice.getCreatedDate()
                ))
                .collect(Collectors.toList());
    }

    public AdminNoticeDetailDTO findNoticeDetail(Long noticeNo) {
        AdminNotice notice = adminNoticeRepository.findById(noticeNo)
                .orElseThrow(() -> new AdminException("해당 공지사항이 존재하지 않습니다."));

        return new AdminNoticeDetailDTO(
                notice.getNoticeNo(),
                notice.getNoticeTitle(),
                notice.getNoticeDesc(),
                notice.getCreatedDate(),
                notice.getModifiedDate()
        );
    }

    @Transactional
    public void registNotice(AdminNoticeRegistRequestDTO requestDTO) {
        validateNoticeInput(requestDTO.getNoticeTitle(), requestDTO.getNoticeDesc());

        AdminNotice adminNotice = AdminNotice.createNotice(
                requestDTO.getNoticeTitle().trim(),
                requestDTO.getNoticeDesc().trim()
        );

        adminNoticeRepository.save(adminNotice);
    }

    @Transactional
    public void updateNotice(AdminNoticeUpdateRequestDTO requestDTO) {
        if (requestDTO.getNoticeNo() == null) {
            throw new AdminException("공지사항 번호는 필수입니다.");
        }

        validateNoticeInput(requestDTO.getNoticeTitle(), requestDTO.getNoticeDesc());

        AdminNotice adminNotice = adminNoticeRepository.findById(requestDTO.getNoticeNo())
                .orElseThrow(() -> new AdminException("수정할 공지사항이 존재하지 않습니다."));

        adminNotice.updateNotice(
                requestDTO.getNoticeTitle().trim(),
                requestDTO.getNoticeDesc().trim()
        );
    }

    private void validateNoticeInput(String noticeTitle, String noticeDesc) {
        if (noticeTitle == null || noticeTitle.trim().isEmpty()) {
            throw new AdminException("공지사항 제목은 필수입니다.");
        }

        if (noticeDesc == null || noticeDesc.trim().isEmpty()) {
            throw new AdminException("공지사항 내용은 필수입니다.");
        }

        if (noticeTitle.trim().length() > 255) {
            throw new AdminException("공지사항 제목은 255자를 초과할 수 없습니다.");
        }
    }
}