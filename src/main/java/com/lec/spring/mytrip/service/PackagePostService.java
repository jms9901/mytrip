package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.PackagePost;

import java.util.List;

public interface PackagePostService {
    // 패키지 상세 조회
    PackagePost getPackageDetails(int packageId);

    // 도시별 패키지 조회
    List<PackagePost> getPackagesByCityId(int cityId);

    // 사용자별 패키지 조회
    List<PackagePost> getPackagesByUserId(int userId);

    // 패키지 상태별 조회
    List<PackagePost> getPackagesByStatus(String status);

    // 패키지 제목 검색
    List<PackagePost> searchPackagesByTitle(String keyword);

    // 패키지 저장
    int savePackage(PackagePost pkg);

    // 패키지 수정
    int updatePackage(PackagePost pkg);

    // 패키지 삭제
    int deletePackage(int packageId, int userId);
}
