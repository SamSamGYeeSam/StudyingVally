package com.samsamgyeesam.studyingvally.domain.course.service;

import com.samsamgyeesam.studyingvally.domain.course.dto.ChapterDTO;
import com.samsamgyeesam.studyingvally.domain.course.entity.Chapter;
import com.samsamgyeesam.studyingvally.domain.course.entity.Course;
import com.samsamgyeesam.studyingvally.domain.course.repository.ChapterRepository;
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
    private final ModelMapper modelMapper;

    // 특정 강의에서 그 강의에 속해 있는 챕터 조회하기
    public List<ChapterDTO> findChaptersByCourseId(Long courseId) {
        List<Chapter> chapterList = chapterRepository.findByCourseId(courseId);

        return chapterList.stream()
                .map(chapter -> modelMapper.map(chapter, ChapterDTO.class))
                .collect(Collectors.toList());
    }

    // 챕터 번호로 챕터 조회
    public ChapterDTO findChapterByChapNo(Long chapNo) {
        Chapter chapter = chapterRepository.findById(chapNo)
                .orElseThrow(() -> new IllegalArgumentException("챕터를 찾을 수 없습니다."));

        return modelMapper.map(chapter, ChapterDTO.class);
    }

    // 챕터 등록
    @Transactional
    public void registChapter(ChapterDTO chapterDTO) {
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

        Chapter foundChapter = chapterRepository.findById(chapNo)
                .orElseThrow(() -> new IllegalArgumentException("챕터를 찾을 수 없습니다."));

        foundChapter.updateChapterInfo(chapTitle, chapDesc, chapUrl);
    }

    // 챕터 삭제
    @Transactional
    public void deleteChapter(Long chapNo) {
        chapterRepository.deleteById(chapNo);
    }
}
