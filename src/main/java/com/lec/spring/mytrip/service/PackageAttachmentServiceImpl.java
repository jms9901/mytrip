package com.lec.spring.mytrip.service;

import com.nimbusds.openid.connect.sdk.assurance.evidences.attachment.Attachment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PackageAttachmentServiceImpl implements PackageAttachmentService {
    //첨부파일 저장
    @Override
    public int saveAttachments(List<MultipartFile> files, int postId) {
        return 0;
    }

    //첨부파일 불러오기
    @Override
    public List<Attachment> getAttachmentsByPostId(int postId) {
        return List.of();
    }

    //첨부파일 삭제
    @Override
    public void deleteAttachment(int attachmentId) {

    }

    //파일 이름 중복 처리
    @Override
    public String generateUniqueFileName(String originalFileName) {
        return "";
    }

    //파일 형식 및 크기 검증
    @Override
    public boolean isValidFile(MultipartFile file) {
        return false;
    }
}
