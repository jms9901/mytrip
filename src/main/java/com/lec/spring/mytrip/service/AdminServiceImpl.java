package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Board;
import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.domain.User;
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

    @Override
    public List<User> findByAuthorityRoleUser(String authority) {
        return adminRepository.findByAuthorityRoleUser(authority);
    }

    @Override
    public List<User> findByAuthorityRoleBusiness(String authority) {
        return adminRepository.findByAuthorityRoleBusiness(authority);
    }

    @Override
    public List<Board> findByBoardCategory(String boardCategory) {
        return adminRepository.findByBoardCategory(boardCategory);
    }

    @Override
    public List<Board> findByFeedCategory(String feedCategory) {
        return adminRepository.findByFeedCategory(feedCategory);
    }

    @Override
    public List<PackagePost> findByAccessPackage(String packageStatus) {
        return adminRepository.findByAccessPackage(packageStatus);
    }

    @Override
    public List<PackagePost> findByStandByPackage(String standBy, String disapproved) {
        return adminRepository.findByStandByPackage(standBy, disapproved);
    }

    @Override
    public List<Payment> findByPayment() {
        return adminRepository.findByPayment();
    }


    @Override
    public void deleteUser(int userId) {
        adminRepository.deleteUser(userId);
    }

    @Override
    public void updateBusinessUserStatus(int userId, String status) {
        adminRepository.updateBusinessUserStatus(userId, status);
    }

    @Override
    public void deleteBoard(int boardId) {
        adminRepository.deleteBoard(boardId);
    }

    @Override
    public void deletePackage(int packageId) {
        adminRepository.deletePackage(packageId);
    }

    @Override
    public void updatePackageStatus(int packageId, String status) {
        adminRepository.updatePackageStatus(packageId, status);
    }



}

