package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.service;

import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.dto.AdminNoticeDetailDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.dto.AdminNoticeListDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.dto.AdminNoticeRegistRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.dto.AdminNoticeUpdateRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.entity.AdminNotice;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.notice.repository.AdminNoticeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 관리자 공지사항 서비스
 *
 * 왜 필요한가:
 * - 공지사항 목록, 상세, 등록, 수정 비즈니스 로직을 처리하기 위함이다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminNoticeService {

    /**
     * 공지사항 Repository
     */
    private final AdminNoticeRepository adminNoticeRepository;

    /**
     * 공지사항 목록 조회
     *
     * @return 공지 목록 DTO 리스트
     */
    public List<AdminNoticeListDTO> findAllNoticeList() {
        List<AdminNotice> noticeList = adminNoticeRepository.findAllByOrderByNoticeNoDesc();
        List<AdminNoticeListDTO> result = new ArrayList<>();

        for (AdminNotice notice : noticeList) {
            result.add(new AdminNoticeListDTO(
                    notice.getNoticeNo(),
                    notice.getNoticeTitle(),
                    notice.getCreatedDate()
            ));
        }

        return result;
    }

    /**
     * 공지사항 상세 조회
     *
     * @param noticeNo 공지 번호
     * @return 공지 상세 DTO
     */
    public AdminNoticeDetailDTO findNoticeDetail(Long noticeNo) {
        AdminNotice notice = adminNoticeRepository.findById(noticeNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 공지사항이 존재하지 않습니다. noticeNo=" + noticeNo));

        return new AdminNoticeDetailDTO(
                notice.getNoticeNo(),
                notice.getNoticeTitle(),
                notice.getNoticeDesc(),
                notice.getCreatedDate(),
                notice.getModifiedDate()
        );
    }

    /**
     * 공지사항 등록
     *
     * @param requestDTO 공지 등록 요청 DTO
     */
    @Transactional
    public void registNotice(AdminNoticeRegistRequestDTO requestDTO) {
        validateNoticeInput(requestDTO.getNoticeTitle(), requestDTO.getNoticeDesc());

        AdminNotice adminNotice = AdminNotice.createNotice(
                requestDTO.getNoticeTitle().trim(),
                requestDTO.getNoticeDesc().trim()
        );

        adminNoticeRepository.save(adminNotice);
    }

    /**
     * 공지사항 수정
     *
     * @param requestDTO 공지 수정 요청 DTO
     */
    @Transactional
    public void updateNotice(AdminNoticeUpdateRequestDTO requestDTO) {
        if (requestDTO.getNoticeNo() == null) {
            throw new IllegalArgumentException("공지사항 번호는 필수입니다.");
        }

        validateNoticeInput(requestDTO.getNoticeTitle(), requestDTO.getNoticeDesc());

        AdminNotice adminNotice = adminNoticeRepository.findById(requestDTO.getNoticeNo())
                .orElseThrow(() -> new EntityNotFoundException("수정할 공지사항이 존재하지 않습니다. noticeNo=" + requestDTO.getNoticeNo()));

        adminNotice.updateNotice(
                requestDTO.getNoticeTitle().trim(),
                requestDTO.getNoticeDesc().trim()
        );
    }

    /**
     * 공지사항 입력값 검증
     *
     * @param noticeTitle 공지 제목
     * @param noticeDesc 공지 내용
     */
    private void validateNoticeInput(String noticeTitle, String noticeDesc) {
        if (noticeTitle == null || noticeTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("공지사항 제목은 필수입니다.");
        }

        if (noticeDesc == null || noticeDesc.trim().isEmpty()) {
            throw new IllegalArgumentException("공지사항 내용은 필수입니다.");
        }

        if (noticeTitle.trim().length() > 255) {
            throw new IllegalArgumentException("공지사항 제목은 255자를 초과할 수 없습니다.");
        }
    }
}