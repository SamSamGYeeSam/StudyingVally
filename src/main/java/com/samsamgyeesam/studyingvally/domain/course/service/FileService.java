package com.samsamgyeesam.studyingvally.domain.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    private ResourceLoader resourceLoader;

    public String saveVideoFile(MultipartFile videoFile) throws IOException {

        if (videoFile == null || videoFile.isEmpty()) {
            return null;
        }

        // 영상 파일 저장 경로 설정
        Resource resource = resourceLoader.getResource("classpath:static/videos");
        String filePath = null;

        if (!resource.exists()) {
            // 폴더가 없으면 생성
            String root = "src/main/resources/static/videos";
            File file = new File(root);
            file.mkdirs();
            filePath = file.getAbsolutePath();
        } else {
            // 폴더가 있으면 절대경로 가져오기
            filePath = resourceLoader.getResource("classpath:static/videos")
                    .getFile()
                    .getAbsolutePath();
        }

        String originalFilename = videoFile.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));

        // UUID 생성
        String savedName = UUID.randomUUID()
                .toString()
                .replace("-", "")
                + ext;

        // 실제 파일 저장
        videoFile.transferTo(new File(filePath + "/" + savedName));

        // db에 저장할 경로 반환
        return "static/videos/" + savedName;
    }
}