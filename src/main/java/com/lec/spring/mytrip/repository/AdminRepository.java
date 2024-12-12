package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.Board;
import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.domain.User;

import java.util.List;

public interface AdminRepository {
    // ROLE_USER 리스트 가져오기
    List<User> findByAuthorityRoleUser(String authority);

    // ROLE_BUSINESS 리스트 가져오기
    List<User> findByAuthorityRoleBusiness(String authority);

    // category 가 소모임인 리스트 가져오기
    List<Board> findByBoardCategory(String boardCategory);

    // category 가 피드인 리스트 가져오기
    List<Board> findByFeedCategory(String feedCategory);

    // package 상태가 승인인 리스트 가져오기
    List<PackagePost> findByAccessPackage(String packageStatus);

    // package 상태가 대기인 리스트 가져오기
    List<PackagePost> findByStandByPackage(String standBy, String disapproved);

    // payment 리스트 가져오기
    List<Payment> findByPayment();

    // 유저 삭제
    void deleteUser(int userId);

    // business 유저 승인 상태로 변경
    void updateBusinessUserStatus(int userId, String status);

    // 소모임/피드 삭제
    void deleteBoard(int boardId);

    // 패키지 삭제
    void deletePackage(int packageId);

    // 패키지 승인 상태 변경
    void updatePackageStatus(int packageId, String status);
}
