package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.UserRepository;
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


@Service
public class businessMypageService {

    private final UserRepository userRepository;
    private final Path uploadDir = Paths.get("uploads/profiles");
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();    // 직전 인스턴스 생성
    @Autowired
    public businessMypageService(UserRepository userRepository) throws IOException {
        this.userRepository = userRepository;
        Files.createDirectories(uploadDir);
    }

    // 사용자 정보 가져오기
    public User getUserById(int userId) {return userRepository.findById(userId);}

    // 개인정보 수정
    // 비밀번호, 프로필 수정
    @Transactional
    public boolean updateCompany(int userId, String currentPassword, String newPassword, MultipartFile profileImage, String profileImageFileName) {

        User user = userRepository.findById(userId);
        if(user == null) {
            return false;
        }

        // 비밀번호 확인 및 업데이트
        if(currentPassword != null && newPassword != null && !newPassword.isEmpty()) {
            if(!user.getPassword().equals(currentPassword)) {
                return false;
            }
            if(!user.getPassword().equals(newPassword)) {
                user.setPassword(newPassword);
            }
        }

        // 프로필 이미지 수정
        // **수정 위치: 프로필 이미지 업데이트 로직 삽입**
        if (profileImageFileName != null && !profileImageFileName.isEmpty()) {
            user.setProfile(profileImageFileName);  // 프로필 이미지 파일 이름 업데이트
        }


        // 프로필 이미지 수정
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                String imageName = saveProfileImage(profileImage);  // 파일 이미지 저장
                user.setProfile(imageName);  // 프로필 이미지 업데이트
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        int result = userRepository.updateCompany(userId, user.getPassword(), user.getProfile());

        return result > 0;

    }

    public String saveProfileImage(MultipartFile profileImage) throws IOException {
        byte[] bytes = profileImage.getBytes();
        String imageName =
    }


    // 기업이 작성한 패키지 리스트 조회
    // 패키지 제목, 내용, 상태(대기, 승인, 미승인), 좋아요 수

    // 패키지 수정
    // 패키지 전체 내용 수정 가능

    // 모든 패키지에 관한 일반 유저가 결제한 결제 내역 -> 결제 상태 조회

}
