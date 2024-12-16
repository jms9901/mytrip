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
    public User getUserById(int userId) {return userRepository.findById(userId);}

    // 개인정보 수정
    // 비밀번호, 프로필 수정
    @Transactional
    public User updateCompany(int userId, String currentPassword, String newPassword, MultipartFile profileImage, String profileImageFileName) {

        User user = userRepository.findById(userId);
        if(user == null && !user.getStatus().equals("승인")) {
            return null;
        }

        // 비밀번호 확인 및 업데이트
        if (currentPassword != null && newPassword != null && !newPassword.isEmpty()) {
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) return null;
            user.setPassword(passwordEncoder.encode(newPassword)); // 암호화 저장
        }

        // 프로필 이미지 저장 및 업데이트
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                String imageName = saveProfileImage(profileImage);
                user.setProfile(imageName);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        // 업데이트 실행
        userRepository.updateCompany(user.getId(), user.getPassword(), user.getProfile());
        return user;

    }

    public String saveProfileImage(MultipartFile profileImage) throws IOException {
        byte[] bytes = profileImage.getBytes();
        String imageName = UUID.randomUUID().toString() + ".jpg";  // UUID 기반 고유 이미지 이름 생성
        Path path = Paths.get("static/uploads/profiles", imageName);
        Files.write(path, bytes);  // 파일 저장
        return imageName;
    }

    // 비밀번호 업데이트
    @Transactional
    public boolean updatePassword(int userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId);
        if (user == null || !user.getPassword().equals(currentPassword)) {
            return false;  // 현재 비밀번호가 일치하지 않으면 실패
        }
        user.setPassword(newPassword);  // 새 비밀번호로 업데이트
        userRepository.save(user);
        return true;
    }

    // 프로필 이미지 업데이트
    @Transactional
    public boolean updateProfileImage(Long userId, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }

        String imageName = saveProfileImage(file);  // 파일을 저장하고 이름 반환
        user.setProfile(imageName);  // 사용자 프로필 이미지 이름 업데이트
        userRepository.save(user);  // DB에 업데이트된 정보 저장
        return true;
    }

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
        return paymentsRepository.getPaymentByCompanyId(userId);
    }

}
