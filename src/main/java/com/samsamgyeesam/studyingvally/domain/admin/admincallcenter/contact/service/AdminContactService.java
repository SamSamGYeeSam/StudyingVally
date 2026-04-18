package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.service;

import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.dto.AdminContactAnswerRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.dto.AdminContactDetailDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.dto.AdminContactListDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.entity.AdminQuestionTech;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.repository.AdminQuestionTechRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 관리자 문의함 서비스
 *
 * 왜 필요한가:
 * - 문의 목록 조회, 상세 조회, 답변 처리 비즈니스 로직을 담당한다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminContactService {

    /**
     * 문의 Repository
     */
    private final AdminQuestionTechRepository adminQuestionTechRepository;

    /**
     * 문의 목록 조회
     *
     * @return 문의 목록 DTO 리스트
     */
    public List<AdminContactListDTO> findAllContactList() {
        List<AdminQuestionTech> contactList = adminQuestionTechRepository.findAllWithUserOrderByQuestionTechNoDesc();
        List<AdminContactListDTO> result = new ArrayList<>();

        for (AdminQuestionTech contact : contactList) {
            result.add(new AdminContactListDTO(
                    contact.getQuestionTechNo(),
                    contact.getQuestionTitle(),
                    contact.getUser().getUserName(),
                    contact.getUser().getUserNickname(),
                    convertQuestionStatusToKorean(contact.getQuestionStatus()),
                    contact.getQuestionAnsweredAt()
            ));
        }

        return result;
    }

    /**
     * 문의 상세 조회
     *
     * @param questionTechNo 문의 번호
     * @return 문의 상세 DTO
     */
    public AdminContactDetailDTO findContactDetail(Long questionTechNo) {
        AdminQuestionTech contact = adminQuestionTechRepository.findDetailByQuestionTechNo(questionTechNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 문의가 존재하지 않습니다. questionTechNo=" + questionTechNo));

        return new AdminContactDetailDTO(
                contact.getQuestionTechNo(),
                contact.getQuestionTitle(),
                contact.getQuestionDesc(),
                contact.getUser().getUserName(),
                contact.getUser().getUserNickname(),
                convertQuestionStatusToKorean(contact.getQuestionStatus()),
                contact.getQuestionAnswer(),
                contact.getQuestionAnsweredAt(),
                contact.getQuestionAnswerUpdatedAt()
        );
    }

    /**
     * 문의 답변 처리
     *
     * @param requestDTO 문의 답변 요청 DTO
     */
    @Transactional
    public void answerContact(AdminContactAnswerRequestDTO requestDTO) {
        if (requestDTO.getQuestionTechNo() == null) {
            throw new IllegalArgumentException("문의 번호는 필수입니다.");
        }

        AdminQuestionTech contact = adminQuestionTechRepository.findById(requestDTO.getQuestionTechNo())
                .orElseThrow(() -> new EntityNotFoundException("답변할 문의가 존재하지 않습니다. questionTechNo=" + requestDTO.getQuestionTechNo()));

        Long adminNo = 1L;
        contact.answerQuestion(requestDTO.getQuestionAnswer(), adminNo);
    }

    /**
     * 문의 상태 한글 변환
     *
     * @param questionStatus 원본 상태값
     * @return 화면 출력용 한글 상태값
     */
    private String convertQuestionStatusToKorean(String questionStatus) {
        if ("RESOLVED".equals(questionStatus)) {
            return "완료";
        }
        return "대기";
    }
}