package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.attachment.PackagePostAndAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PackagePostService {
    // 패키지 상세 조회
    PackagePostAndAttachment getPackageDetails(int packageId);

    // 도시별 패키지 조회
    List<PackagePost> getPackagesByCityId(int cityId);

    // 사용자별 패키지 조회
    List<PackagePost> getPackagesByUserId(int userId);

    // 패키지 상태별 조회
    List<PackagePost> getPackagesByStatus(String status);

    // 패키지 검색
    List<PackagePost> searchPackages(String keyword);

    // 패키지 저장
    int savePackage(PackagePost pkg, List<MultipartFile> files);

    // 패키지 수정
    int updatePackage(PackagePost pkg, List<MultipartFile> files);

    // 패키지 삭제
    int deletePackage(int packageId, int userId);

    // 마이페이지 패키지 상세 조회
    List<PackagePost> getPackageDetailsById(int packageId);
}
