package com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.service;

import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.dto.AdminContactAnswerRequestDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.dto.AdminContactDetailDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.dto.AdminContactListDTO;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.entity.AdminQuestionTech;
import com.samsamgyeesam.studyingvally.domain.admin.admincallcenter.contact.repository.AdminQuestionTechRepository;
import com.samsamgyeesam.studyingvally.domain.admin.exception.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 관리자 문의함 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminContactService {

    private final AdminQuestionTechRepository adminQuestionTechRepository;

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

    public AdminContactDetailDTO findContactDetail(Long questionTechNo) {
        AdminQuestionTech contact = adminQuestionTechRepository.findDetailByQuestionTechNo(questionTechNo)
                .orElseThrow(() -> new AdminException("해당 문의가 존재하지 않습니다."));

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

    @Transactional
    public void answerContact(AdminContactAnswerRequestDTO requestDTO) {
        if (requestDTO.getQuestionTechNo() == null) {
            throw new AdminException("문의 번호는 필수입니다.");
        }

        if (requestDTO.getQuestionAnswer() == null || requestDTO.getQuestionAnswer().trim().isEmpty()) {
            throw new AdminException("답변 내용을 입력해 주세요.");
        }

        AdminQuestionTech contact = adminQuestionTechRepository.findById(requestDTO.getQuestionTechNo())
                .orElseThrow(() -> new AdminException("답변할 문의가 존재하지 않습니다."));

        if (contact.getQuestionAnswer() != null && !contact.getQuestionAnswer().trim().isEmpty()) {
            throw new AdminException("이미 답변이 완료된 문의입니다.");
        }

        Long adminNo = 1L;
        contact.answerQuestion(requestDTO.getQuestionAnswer(), adminNo);
    }

    private String convertQuestionStatusToKorean(String questionStatus) {
        if ("RESOLVED".equals(questionStatus)) {
            return "완료";
        }
        return "대기";
    }
}