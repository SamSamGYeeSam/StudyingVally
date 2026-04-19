package com.samsamgyeesam.studyingvally.domain.course.service;

import com.samsamgyeesam.studyingvally.domain.course.dto.ChapterDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.Chapter;
import com.samsamgyeesam.studyingvally.domain.course.entity.Course;
import com.samsamgyeesam.studyingvally.domain.course.exception.CourseException;
import com.samsamgyeesam.studyingvally.domain.course.repository.ChapterRepository;
import com.samsamgyeesam.studyingvally.domain.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChapterService {
    // 강의관련 서비스 로직
    /* 1. 전체 강의 조회
     *  2. 수강생 조회
     *  3. */

    private final ChapterRepository chapterRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    // 특정 강의에서 그 강의에 속해 있는 챕터 조회하기
    // 강의에 챕터가 없을 수도 있지
    public List<ChapterDTO> findChaptersByCourseId(Long courseId) {

        // 강의 누르고 -> 챕터 보기
        // 강의없는 경우는 예외처리 안 함

        List<Chapter> chapterList = chapterRepository.findByCourseId(courseId);

        return chapterList.stream()
                .map(chapter -> modelMapper.map(chapter, ChapterDTO.class))
                .collect(Collectors.toList());
    }

    // 챕터 번호로 챕터 조회
    // 없으면 예외처리
    public ChapterDTO findChapterByChapNo(Long chapNo) {
        Chapter chapter = chapterRepository.findById(chapNo)
                .orElseThrow(() -> new CourseException("해당 챕터를 찾을 수 없습니다."));

        return modelMapper.map(chapter, ChapterDTO.class);
    }

    // 챕터 등록
    @Transactional
    public void registChapter(ChapterDTO chapterDTO) {

        if (chapterDTO.getChapTitle() == null || chapterDTO.getChapTitle().trim().isEmpty()) {
            throw new CourseException("챕터 제목을 입력해주세요.");
        }
        if (chapterDTO.getChapDesc() == null || chapterDTO.getChapDesc().trim().isEmpty()) {
            throw new CourseException("챕터 설명을 입력해주세요.");
        }
        if (chapterDTO.getChapUrl() == null || chapterDTO.getChapUrl().trim().isEmpty()) {
            throw new CourseException("강의 영상을 업로드해주세요.");
        }

        Chapter chapter = new Chapter(
                chapterDTO.getChapTitle(),
                chapterDTO.getChapDesc(),
                chapterDTO.getChapUrl(),
                chapterDTO.getCourseId()
        );

        chapterRepository.save(chapter);
    }

    //챕터 수정
    @Transactional
    public void updateChapter(Long chapNo, String chapTitle, String chapDesc, String chapUrl) {

        // 혹시 몰라서
        if (chapTitle == null || chapTitle.trim().isEmpty()) {
            throw new CourseException("챕터 제목을 입력해주세요.");
        }
        if (chapDesc == null || chapDesc.trim().isEmpty()) {
            throw new CourseException("챕터 설명을 입력해주세요.");
        }

        Chapter foundChapter = chapterRepository.findById(chapNo)
                .orElseThrow(() -> new CourseException("해당 챕터가 존재하지 않습니다."));

        // 수정할 때 영상 선택한 하면 기존에 올려둔 영상으로 유지
        if (chapUrl == null) {
            chapUrl = foundChapter.getChapUrl();
        }

        foundChapter.updateChapterInfo(chapTitle, chapDesc, chapUrl);
    }

    // 챕터 삭제
    @Transactional
    public String deleteChapter(Long chapNo) {

        Chapter chapter = chapterRepository.findById(chapNo)
                .orElseThrow(() -> new CourseException("삭제할 챕터가 존재하지 않습니다."));

        String title = chapter.getChapTitle();

        chapterRepository.delete(chapter);

        return title;
    }
}
