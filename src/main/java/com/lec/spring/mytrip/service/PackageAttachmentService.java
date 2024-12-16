package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.attachment.PackagePostAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PackageAttachmentService {
    //첨부파일 저장
    int savePackageAttachments(List<MultipartFile> files, PackagePost packagePost);
    //첨부파일 불러오기
    List<PackagePostAttachment> getAttachmentsByPostId(int postId);
    //첨부파일 삭제
    void deleteAttachment(int attachmentId);

    int savePostAttachments(List<MultipartFile> files, Feed feed);

    //파일 이름 중복 처리
    String generateUniqueFileName(String originalFileName, int userId);
    //파일 형식 및 크기 검증
    boolean isValidFile(MultipartFile file);

    //이 게시글에 첨부파일이 있는가
    List<PackagePostAttachment> findByPackageId(int Id);
}
