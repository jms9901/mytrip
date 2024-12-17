package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.PackagePost;

import java.util.List;

public interface PackagePostRepository {
    // 패키지 상세 조회
    PackagePost findById(int packageId);

    // 도시별 패키지 조회
    List<PackagePost> findByCityId(int cityId);

    // 사용자별 패키지 조회
    List<PackagePost> findByUserId(int userId);

    List<PackagePost> findByCityAndStatus(int userId);

    // 패키지 상태별 조회
    List<PackagePost> findByStatus(String status);

    // 패키지 제목 검색
    List<PackagePost> searchByTitle(String title);

    // 패키지 저장
    int save(PackagePost pkg);

    // 패키지 수정
    int update(PackagePost pkg);

    // 패키지 삭제
    int deleteById(int packageId);
}
