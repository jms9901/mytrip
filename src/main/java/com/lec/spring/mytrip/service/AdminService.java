package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Board;
import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.domain.attachment.BoardAttachment;
import com.lec.spring.mytrip.domain.attachment.PackagePostAttachment;

import java.util.List;

public interface AdminService {
    List<User> findByAuthorityRoleUser(String authority);
    List<User> findByAuthorityRoleBusiness(String authority);
    List<Board> findByBoardCategory(String boardCategory);
    List<Board> findByFeedCategory(String feedCategory);
    List<PackagePost> findByAccessPackage(String packageStatus);
    List<PackagePost> findByStandByPackage(String standBy, String disapproved);
    List<Payment> findByPayment();

    void deleteUser(int userId);
    void updateBusinessUserStatus(int userId, String status);
    void deleteBoard(int boardId);
    void deletePackage(int packageId);
    void updatePackageStatus(int packageId, String status);
    List<PackagePostAttachment> findPackageAttachments(int packageId);


    List<BoardAttachment> findBoardAttachments(int boardId);


}
