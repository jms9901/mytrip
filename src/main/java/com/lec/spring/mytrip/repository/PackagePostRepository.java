package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.PackagePost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PackagePostRepository {
    // 패키지 상세 조회
    PackagePost findById(int packageId);

    // 도시별 패키지 조회
    List<PackagePost> findByCityId(int cityId);

    // 사용자별 패키지 조회
    List<PackagePost> findByUserId(@Param("userId")int userId);

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

    // 기업 회원이 등록한 패키지 리스트와 그에 해당 패키지의 좋아요 수
    List<PackagePost> likeCntByPackage (int userId);

    // 도시 이름과 함께 패키지 상세정보 조회
    List<PackagePost> mypagePackageDetail (int packageId);
}
