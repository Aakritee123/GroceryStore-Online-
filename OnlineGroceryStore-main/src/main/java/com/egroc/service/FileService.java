// File: src/main/java/com/egroc/service/FileService.java
package com.egroc.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {



    public String saveFile(MultipartFile file, String uploadDir) throws IOException {

        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        String imageUUID = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, imageUUID);
        Files.write(filePath, file.getBytes());
        return imageUUID;
    }
}

