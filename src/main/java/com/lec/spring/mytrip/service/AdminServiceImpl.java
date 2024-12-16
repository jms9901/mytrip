package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Board;
import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.domain.attachment.BoardAttachment;
import com.lec.spring.mytrip.domain.attachment.PackagePostAttachment;
import com.lec.spring.mytrip.repository.AdminRepository;
import com.lec.spring.mytrip.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(SqlSession sqlSession, PasswordEncoder passwordEncoder) {
        this.adminRepository = sqlSession.getMapper(AdminRepository.class);
        this.passwordEncoder = passwordEncoder;
    }


    // NOTE: USER TABLE 관련
    // user 테이블 불러오기
    @Override
    public List<User> findByAuthorityRoleUser(String authority) {
        return adminRepository.findByAuthorityRoleUser(authority);
    }

    // business 테이블 불러오기
    @Override
    public List<User> findByAuthorityRoleBusiness(String authority) {
        return adminRepository.findByAuthorityRoleBusiness(authority);
    }

    // user 삭제
    @Override
    public void deleteUser(int userId) {
        adminRepository.deleteUser(userId);
    }

    // 비즈니스 유저 상태 변경
    @Override
    public void updateBusinessUserStatus(int userId, String status) {
        adminRepository.updateBusinessUserStatus(userId, status);
    }

    // NOTE : 게시글 (소모임 + 피드 )

    // board 테이블 불러오기
    @Override
    public List<Board> findByBoardCategory(String boardCategory) {
        return adminRepository.findByBoardCategory(boardCategory);
    }

    // 피드 테이블 불러오기
    @Override
    public List<Board> findByFeedCategory(String feedCategory) {
        return adminRepository.findByFeedCategory(feedCategory);
    }

    // 게시글 삭제
    @Override
    public void deleteBoard(int boardId) {
        adminRepository.deleteBoard(boardId);
    }

    @Override
    public List<BoardAttachment> findBoardAttachments(int boardId) {
        return adminRepository.findBoardAttachments(boardId);
    }


    // NOTE : 패키지

    // 승인된 패키지 불러오기
    @Override
    public List<PackagePost> findByAccessPackage(String packageStatus) {
        return adminRepository.findByAccessPackage(packageStatus);
    }

    // 대기 및 미승인 패키지 불러오기
    @Override
    public List<PackagePost> findByStandByPackage(String standBy, String disapproved) {
        return adminRepository.findByStandByPackage(standBy, disapproved);
    }

    // 패키지 삭제하기
    @Override
    public void deletePackage(int packageId) {
        adminRepository.deletePackage(packageId);
    }

    // 패키지 상태 업그레이드
    @Override
    public void updatePackageStatus(int packageId, String status) {
        adminRepository.updatePackageStatus(packageId, status);
    }

    @Override
    public List<PackagePostAttachment> findPackageAttachments(int packageId) {
        return adminRepository.findPackageAttachments(packageId);
    }



    // NOTE : 결제관리 메뉴
    // 결제내역 정보 불러오기

    @Override
    public List<Payment> findByPayment() {
        return adminRepository.findByPayment();
    }



}

