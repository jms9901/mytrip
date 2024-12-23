package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.Payment;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.PackagePostRepository;
import com.lec.spring.mytrip.repository.PaymentsRepository;
import com.lec.spring.mytrip.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class BusinessMypageService {

    private final UserRepository userRepository;
    private final PackagePostRepository packagePostRepository;
    private final PaymentsRepository paymentsRepository;
    private final Path uploadDir = Paths.get("uploads/profiles");
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();    // 직전 인스턴스 생성

    @Autowired
    public BusinessMypageService(UserRepository userRepository, PackagePostRepository packagePostRepository, PaymentsRepository paymentsRepository) throws IOException {
        this.userRepository = userRepository;
        this.packagePostRepository = packagePostRepository;
        this.paymentsRepository = paymentsRepository;
        Files.createDirectories(uploadDir);
    }

    // 사용자 정보 가져오기
    public User getUserById(int id) {return userRepository.findById(id);}


    // 개인정보 수정
    // 비밀번호, 프로필 수정
    @Transactional
    public boolean updateCompany(int userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId);

        // 사용자 정보 없으면 실패
        if(user == null) {
            return false;
        }

        // 사용자 기업 확인
        if(!"ROLE_BUSINESS".equals(user.getAuthorization())) {
            return false;
        }

        // 사용자 상태 확인
        if(!"승인".equals(user.getStatus())) {
            return false;
        }

        // 비밀번호 확인 및 업데이트
        if (currentPassword != null && newPassword != null && !newPassword.isEmpty()) {
            // 현재 비밀번호가 맞는 지 확인
            if (!user.getPassword().equals(currentPassword)) {
                return false; // 현재 비밀번호 불일치
            }

            // 새로운 비밀번호가 기존 비밀번호와 다를 경우에만 업데이트
            if (!user.getPassword().equals(newPassword)) {
                user.setPassword(newPassword);  // 평문 비밀번호 업데이트
            }
        }

        // updateUser 메서드를 호출하여 업데이트된 행의 수 반환
        int result = userRepository.updateCompany(userId, user.getPassword());

        return result > 0; // 업데이트가 성공하면 true, 실패하면 false 반환
    }

    // 비밀번호 업데이트 => mypageService 가져다 쓰기
    // 프로필 이미지 업데이트 => mypageService 가져다 쓰기

    // 기업이 작성한 패키지 리스트 조회
    // 패키지 제목, 내용, 상태(대기, 승인, 미승인), 좋아요 수
    @Transactional
    public List<PackagePost> likeCntByPackage(int userId) {
        List<PackagePost> packages = packagePostRepository.likeCntByPackage(userId);

        // 디버깅용 로그
        log.info("Fetch packages: {}", packages);
        return packagePostRepository.likeCntByPackage(userId);
    }


    // 모든 패키지에 관한 일반 유저가 결제한 결제 내역 -> 결제 상태 조회
    @Transactional
    public List<Payment> getPaymentByCompanyId(int userId) {
        System.out.println("Business payments List service 진입");
        List<Payment> paymentByCompanyId = paymentsRepository.getPaymentByCompanyId(userId);
        paymentByCompanyId.forEach(payment -> log.info("Payment: {}", payment));
        return paymentByCompanyId;
    }

}
