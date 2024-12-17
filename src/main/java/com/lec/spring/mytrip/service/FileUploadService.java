package com.lec.spring.mytrip.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadService {

    private static final String UPLOAD_DIR = "/path/to/upload/directory";

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR, fileName);
        file.transferTo(path);
        return fileName;
    }
}
