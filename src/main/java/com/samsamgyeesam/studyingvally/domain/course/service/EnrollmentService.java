package com.samsamgyeesam.studyingvally.domain.course.service;

import com.samsamgyeesam.studyingvally.domain.course.dto.EnrollmentDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.Enrollment;
import com.samsamgyeesam.studyingvally.domain.course.repository.EnrollmentRepository;
import com.samsamgyeesam.studyingvally.domain.user.entity.UserUser;
import com.samsamgyeesam.studyingvally.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ModelMapper modelMapper;



    // 특정 강의의 수강생 조회
    public List<EnrollmentDTO> findStudentsByCourseId(Long courseId) {
        List<Enrollment> enrollmentList = enrollmentRepository.findByCourseIdOrderByEnrollmentProcessDesc(courseId);

        return enrollmentList.stream()
                .map(enrollment -> {
                    EnrollmentDTO dto = modelMapper.map(enrollment, EnrollmentDTO.class);

                    // 사용자 이름, 닉네임 가져오기
                    if (enrollment.getUserNo() != null) {
                        UserUser user = userRepository.findById(enrollment.getUserNo())
                                .orElse(null);
                        if (user != null) {
                            dto.setUserName(user.getUserName());
                            dto.setUserNickname(user.getUserNickname());
                        }
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }
}