package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.attachment.PackagePostAttachment;

import java.util.List;

public interface PackageAttachmentRepository {
    // 첨부파일 저장
    int insertAttachment(PackagePostAttachment attachment);

    // 패키지 ID로 첨부파일 목록 조회
    List<PackagePostAttachment> findByPackageId(Long packageId);

    // 첨부파일 ID로 첨부파일 조회. 일단 만들었는데 썸네일에 쓸 수 있나?
    PackagePostAttachment findByAttachmentId(Long packageAttachmentId);

    // 첨부파일 삭제
    int deleteAttachment(int packageAttachmentId);

    // 첨부파일 이름 중복 처리 (중복된 이름이 있는지 체크)
    boolean isFileNameExists(String fileName);
}
