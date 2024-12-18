package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class MyPageService {

    private final UserRepository userRepository;
    private final Path uploadDir = Paths.get("uploads/profiles");  // static 경로로 수정
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  // 직접 인스턴스 생성

    @Autowired
    public MyPageService(UserRepository userRepository) throws IOException {
        this.userRepository = userRepository;
        Files.createDirectories(uploadDir);  // 업로드 폴더 생성
    }

    // 사용자 정보 가져오기
    public User getUserById(int userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public boolean updateUser(int userId, String introduction, String currentPassword, String newPassword, MultipartFile profileImage, String profileImageFileName) {
        // 사용자 정보를 가져옴
        User user = userRepository.findById(userId);
        if (user == null) {
            return false; // 사용자 정보가 없으면 실패
        }

        // 비밀번호 확인 및 업데이트
        if (currentPassword != null && newPassword != null && !newPassword.isEmpty()) {
            // 현재 비밀번호가 맞는지 확인
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {  // bcrypt 비교
                return false;  // 현재 비밀번호가 틀리면 실패
            }
            // 새로운 비밀번호가 기존 비밀번호와 다를 경우에만 업데이트
            if (!passwordEncoder.matches(newPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));  // 암호화하여 새 비밀번호 저장
            }
        }

        // 자기소개 수정
        if (introduction != null && !introduction.isEmpty()) {
            user.setIntroduction(introduction);  // 자기소개 업데이트
        }

        // 프로필 이미지 파일 이름을 업데이트
        if (profileImageFileName != null && !profileImageFileName.isEmpty()) {
            user.setProfile(profileImageFileName);  // 프로필 이미지 파일 이름 업데이트
        }

        // 프로필 이미지 수정 (MultipartFile로 전달된 이미지 처리)
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                String imageName = saveProfileImage(profileImage);  // 파일 이미지 저장
                user.setProfile(imageName);  // 프로필 이미지 업데이트
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        // updateUser 메서드를 호출하여 업데이트된 행의 수 반환
        int result = userRepository.updateUser(userId, user.getPassword(), user.getIntroduction(), user.getProfile());

        return result > 0; // 업데이트가 성공하면 true, 실패하면 false 반환
    }

    // 프로필 이미지 저장 메서드 (MultipartFile을 실제 파일로 저장)
    public String saveProfileImage(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String imageName = UUID.randomUUID().toString() + ".jpg";  // UUID 기반 고유 이미지 이름 생성
        Path path = Paths.get(uploadDir.toString(), imageName);  // uploads/profiles 폴더에 저장
        Files.write(path, bytes);  // 파일 저장
        return imageName;  // 파일 이름 반환
    }

    // 비밀번호 업데이트
    @Transactional
    public boolean updatePassword(int userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId);
        if (user == null || !passwordEncoder.matches(currentPassword, user.getPassword())) {  // bcrypt 비교
            return false;  // 현재 비밀번호가 일치하지 않으면 실패
        }
        user.setPassword(passwordEncoder.encode(newPassword));  // 새 비밀번호로 업데이트
        userRepository.save(user);
        return true;
    }

    // 자기소개 업데이트
    @Transactional
    public boolean updateIntroduction(int userId, String introduction) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return false;
        }
        user.setIntroduction(introduction);
        userRepository.save(user);
        return true;
    }

    // 프로필 이미지 업데이트
    @Transactional
    public boolean updateProfileImage(int userId, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId);
//                .orElse(null);
        if (user == null) {
            return false;
        }

        // 기존 프로필 이미지가 있을 경우 삭제
        String existingImageName = user.getProfile();
        if (existingImageName != null && !existingImageName.isEmpty()) {
            Path existingImagePath = Paths.get(uploadDir.toString(), existingImageName);
            Files.deleteIfExists(existingImagePath);  // 기존 파일 삭제
        }

        String imageName = saveProfileImage(file);  // 파일을 저장하고 이름 반환
        user.setProfile(imageName);  // 사용자 프로필 이미지 이름 업데이트
        userRepository.save(user);  // DB에 업데이트된 정보 저장
        return true;
    }
}
