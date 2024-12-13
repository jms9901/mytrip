package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.repository.PackagePostRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackagePostServiceImpl implements PackagePostService {
    private final PackagePostRepository packagePostRepository;

    @Autowired
    public PackagePostServiceImpl(SqlSession sqlSession) {
        this.packagePostRepository = sqlSession.getMapper(PackagePostRepository.class);
    }

    // 패키지 상세
    @Override
    public PackagePost getPackageDetails(int packageId) {
        // 패키지 ID 검증
        if (packageId <= 0) {
            throw new IllegalArgumentException("유효하지 않은 패키지 ID입니다.");
        }
        // 패키지 데이터 조회
        PackagePost pkg = packagePostRepository.findById(packageId);
        if (pkg == null) {
            throw new IllegalArgumentException("ID가 " + packageId + "인 패키지를 찾을 수 없습니다.");
        }
        return pkg;
    }

    //도시 별 패키지 목록
    @Override
    public List<PackagePost> getPackagesByCityId(int cityId) {
        // 도시 ID 검증
        if (cityId <= 0) {
            throw new IllegalArgumentException("유효하지 않은 도시 ID입니다.");
        }
        return packagePostRepository.findByCityId(cityId);
    }

    //검색
    @Override
    public List<PackagePost> searchPackages(String keyword) {
        // 검색 키워드 검증
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색 키워드는 null이거나 비어 있을 수 없습니다.");
        }
        return packagePostRepository.searchByTitle(keyword);
    }

    // 패키지 저장
    @Override
    public int savePackage(PackagePost pkg) {
        // 패키지 데이터 검증
        if (pkg == null) {
            throw new IllegalArgumentException("패키지 정보가 null일 수 없습니다.");
        }
        if (pkg.getCityId() <= 0 || pkg.getUserId() <= 0 || pkg.getPackageTitle() == null || pkg.getPackageTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 패키지 데이터입니다. 도시 ID, 사용자 ID, 제목은 필수입니다.");
        }
        return packagePostRepository.save(pkg);
    }

    //패키지 수정
    @Override
    public int updatePackage(PackagePost pkg) {
        // 패키지 데이터 검증
        if (pkg == null || pkg.getPackageId() <= 0) {
            throw new IllegalArgumentException("패키지 또는 패키지 ID가 null일 수 없습니다.");
        }

        // 기존 데이터 조회 및 검증
        PackagePost existingPackage = packagePostRepository.findById(pkg.getPackageId());
        if (existingPackage == null) {
            throw new IllegalArgumentException("ID가 " + pkg.getPackageId() + "인 패키지를 찾을 수 없습니다.");
        }
        if (existingPackage.getUserId() != pkg.getUserId()) {
            throw new SecurityException("이 패키지를 수정할 권한이 없습니다.");
        }
        return packagePostRepository.update(pkg);
    }

    // 패키지 삭제
    @Override
    public int deletePackage(int packageId, int userId) {
        // 데이터 검증
        if (packageId <= 0 || userId <= 0) {
            throw new IllegalArgumentException("패키지 ID와 사용자 ID는 null이거나 0 이하일 수 없습니다.");
        }

        // 기존 데이터 조회 및 검증
        PackagePost pkg = packagePostRepository.findById(packageId);
        if (pkg == null) {
            throw new IllegalArgumentException("ID가 " + packageId + "인 패키지를 찾을 수 없습니다.");
        }
        if (pkg.getUserId() != userId) {
            throw new SecurityException("이 패키지를 삭제할 권한이 없습니다.");
        }
        return packagePostRepository.deleteById(packageId);
    }



    // 유저(기업 계정) 별 패키지 목록 > 마이페이지
    @Override
    public List<PackagePost> getPackagesByUserId(int userId) {
        // 사용자 ID 검증
        if (userId <= 0) {
            throw new IllegalArgumentException("유효하지 않은 사용자 ID입니다.");
        }
        // 권한이 기업인지도 추가해야함
        return packagePostRepository.findByUserId(userId);
    }

    // 상태에 따른 조회 > 관리자 페이지?
    @Override
    public List<PackagePost> getPackagesByStatus(String status) {
        // 상태값 검증
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("상태값은 null이거나 비어 있을 수 없습니다.");
        }
        return packagePostRepository.findByStatus(status);
    }
} // end class

