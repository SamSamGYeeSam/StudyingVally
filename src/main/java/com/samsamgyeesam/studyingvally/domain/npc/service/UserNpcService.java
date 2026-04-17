package com.samsamgyeesam.studyingvally.domain.npc.service;

import com.samsamgyeesam.studyingvally.domain.npc.dto.UserNpcQuestionTechDTO;
import com.samsamgyeesam.studyingvally.domain.npc.entity.UserNpcQuestionTech;
import com.samsamgyeesam.studyingvally.domain.npc.repository.UserNpcQuestionTechRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserNpcService {

    private final UserNpcQuestionTechRepository repository;

    @Transactional
    public void registInquiry(UserNpcQuestionTechDTO dto, Long userNo) {
        UserNpcQuestionTech entity = new UserNpcQuestionTech();
        entity.setQuestionTitle(dto.getQuestionTitle());
        entity.setQuestionDesc(dto.getQuestionDesc());
        entity.setUserNo(userNo);
        entity.setQuestionStatus("답변 대기");

        repository.save(entity);
    }

    // ✨ [수정됨] Entity가 아닌 DTO 리스트를 반환하도록 변경
    @Transactional(readOnly = true)
    public List<UserNpcQuestionTechDTO> getMyInquiries(Long userNo) {
        List<UserNpcQuestionTech> entities = repository.findByUserNoOrderByQuestionTechNoDesc(userNo);

        // Entity 리스트를 DTO 리스트로 깔끔하게 변환 (Stream 활용)
        return entities.stream().map(entity -> {
            UserNpcQuestionTechDTO dto = new UserNpcQuestionTechDTO();
            dto.setQuestionTechNo(entity.getQuestionTechNo());
            dto.setQuestionTitle(entity.getQuestionTitle());
            dto.setQuestionDesc(entity.getQuestionDesc());
            dto.setQuestionStatus(entity.getQuestionStatus());
            dto.setQuestionAnswer(entity.getQuestionAnswer());
            return dto;
        }).collect(Collectors.toList());
    }
}