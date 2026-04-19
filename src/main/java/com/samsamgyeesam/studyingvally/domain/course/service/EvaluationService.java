package com.samsamgyeesam.studyingvally.domain.course.service;

import com.samsamgyeesam.studyingvally.domain.course.dto.EvaluationDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.Evaluation;
import com.samsamgyeesam.studyingvally.domain.course.repository.EvaluationRepository;
import com.samsamgyeesam.studyingvally.domain.user.entity.UserUser;
import com.samsamgyeesam.studyingvally.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final ModelMapper modelMapper;

    // 특정 강의의 강의평 조회
    public List<EvaluationDTO> findEvaluationsByCourseId(Long courseId) {
        List<Evaluation> evaluationList = evaluationRepository.findByCourseIdWithUser(courseId);

        return evaluationList.stream()
                .map(evaluation -> {
                    EvaluationDTO evaluationDTO = modelMapper.map(evaluation, EvaluationDTO.class);

                    // 이름, 닉네임 가져오기
                    if (evaluation.getUser() != null) {
                        evaluationDTO.setUserName(evaluation.getUser().getUserName());
                        evaluationDTO.setUserNickname(evaluation.getUser().getUserNickname());
                    }

                    return evaluationDTO;
                })
                .collect(Collectors.toList());
    }

}