package com.samsamgyeesam.studyingvally.domain.course.service;

import org.springframework.beans.factory.annotation.Value;
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

//    @Value("${file.upload-dir}")
    @Value("${file.upload-dir:C:/upload}")
    private String uploadDir;

    public String saveVideoFile(MultipartFile videoFile) throws IOException {

        if (videoFile.isEmpty()) {
            return null;
        }

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String originalFilename = videoFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        Path filePath = Paths.get(uploadDir, uniqueFilename);
        Files.write(filePath, videoFile.getBytes());

        return "/uploads/videos/" + uniqueFilename;
    }
}