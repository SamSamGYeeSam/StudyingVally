package com.samsamgyeesam.studyingvally.domain.course.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentReviewResponseDTO {
    private String content;
    private Double score;
    private String nickname;

//    public ReviewResponseDTO(Review review, String nickname) {
//        this.content = review.getContent();
//        this.score = review.getScore();
//        this.nickname = nickname;
//    }
    public StudentReviewResponseDTO(String content, Double score, String nickname) {
        this.content = content;
        this.score = score;
        this.nickname = nickname;
    }

}
