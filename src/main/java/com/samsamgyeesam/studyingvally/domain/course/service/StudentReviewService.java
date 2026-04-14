package com.samsamgyeesam.studyingvally.domain.course.service;

import com.samsamgyeesam.studyingvally.domain.course.dto.StudentReviewResponseDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.StudentReview;
import com.samsamgyeesam.studyingvally.domain.course.repository.StudentReviewRepository;
import com.samsamgyeesam.studyingvally.domain.user.repository.StudentUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentReviewService {


    private final StudentReviewRepository studentReviewRepository;
    private final StudentUserRepository studentUserRepository;

public List<StudentReviewResponseDTO> getReviewsByCourseId(Long courseId) {
        List<StudentReview> reviews = studentReviewRepository.findByCourseId(courseId);
    System.out.println("조회된 리뷰 개수: " + reviews.size());

        return reviews.stream().map(review -> {
            String nickname = "익명";

            if (review.getUser() != null) {
                nickname = review.getUser().getUserNickname();
            } else {
                System.out.println("실패: Review 엔티티의 user가 null입니다.");
            }

            return new StudentReviewResponseDTO(review.getContent(), review.getScore(), nickname);
        }).collect(Collectors.toList());
    }


}
