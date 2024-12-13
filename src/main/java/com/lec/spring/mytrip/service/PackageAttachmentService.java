package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.PackagePost;
import com.nimbusds.openid.connect.sdk.assurance.evidences.attachment.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PackageAttachmentService {
    //첨부파일 저장
    int saveAttachments(List<MultipartFile> files, PackagePost packagePost);
    //첨부파일 불러오기
    List<Attachment> getAttachmentsByPostId(int postId);
    //첨부파일 삭제
    void deleteAttachment(int attachmentId);
    //파일 이름 중복 처리
    String generateUniqueFileName(String originalFileName);
    //파일 형식 및 크기 검증
    boolean isValidFile(MultipartFile file);
}
