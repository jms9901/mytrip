package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.PostAttachment;
import com.lec.spring.mytrip.repository.PostAttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class PostAttachmentServiceImpl implements PostAttachmentService {

    private static final String BASE_PATH = "uploads";
    @Override
    public PostAttachment findById(Long id) {
        return null;
    }

    @Override
    public String saveImgFile(String folderName, String fileName, MultipartFile file) throws Exception {
        String dirPath = BASE_PATH + File.separator + folderName + File.separator + fileName;
        File fileDir = new File(dirPath);

        if(!fileDir.exists()){
            fileDir.mkdirs();
        }

        String filePath = dirPath + File.separator + fileName;
        File saveFile = new File(filePath);

        file.transferTo(saveFile);

        return filePath;
    }

    @Override
    public boolean deleteImgFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.delete();
    }
}
