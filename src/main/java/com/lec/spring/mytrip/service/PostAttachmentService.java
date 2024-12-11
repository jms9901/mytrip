package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.PostAttachment;
import org.springframework.web.multipart.MultipartFile;


public interface PostAttachmentService {
    PostAttachment findById(Long id);

    String saveImgFile(String folderName, String fileName, MultipartFile file) throws Exception;

    boolean deleteImgFile(String filePath);
}
