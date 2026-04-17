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
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    // 특정 강의의 강의평 조회
    public List<EvaluationDTO> findEvaluationsByCourseId(Long courseId) {
        List<Evaluation> evaluationList = evaluationRepository.findByCourseIdOrderByEvaluationNoDesc(courseId);

        return evaluationList.stream()
                .map(evaluation -> {
                    EvaluationDTO dto = modelMapper.map(evaluation, EvaluationDTO.class);

                    // 이름, 닉네임 가져오기
                    if (evaluation.getUserNo() != null) {
                        UserUser user = userRepository.findById(evaluation.getUserNo()).orElse(null);
                        if (user != null) {
                            dto.setUserName(user.getUserName());           // 이름 추가
                            dto.setUserNickname(user.getUserNickname());   // 닉네임 추가
                        }
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

}