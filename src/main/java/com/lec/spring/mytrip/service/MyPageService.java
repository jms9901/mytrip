package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class MyPageService {

    private final UserRepository userRepository;
    private final Path uploadDir = Paths.get("uploads/profiles");

    @Autowired
    public MyPageService(UserRepository userRepository) throws IOException {
        this.userRepository = userRepository;
        Files.createDirectories(uploadDir);  // 업로드 폴더 생성
    }

    // 특정 사용자 정보를 DB에서 가져오기
    public User getUserById(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.orElse(null); // 없으면 null 반환
    }

    // 비밀번호 수정
    public boolean updatePassword(Long userId, String currentPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(currentPassword)) {
                user.setPassword(newPassword);
                userRepository.updateUser(userId, newPassword, user.getIntroduction(), user.getProfile());
                return true;
            }
        }
        return false;
    }

    // 자기소개 수정
    public boolean updateIntroduction(Long userId, String introduction) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setIntroduction(introduction);
            userRepository.updateUser(userId, user.getPassword(), introduction, user.getProfile());
            return true;
        }
        return false;
    }

    // 프로필 이미지 업로드
    public boolean updateProfileImage(Long userId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            // 파일이 null이거나 비어있을 경우 처리
            return false;
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            String fileName = "user_" + userId + "_" + file.getOriginalFilename();
            Path targetLocation = uploadDir.resolve(fileName);

            // 디렉토리가 존재하는지 확인하고 없으면 생성
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir); // 업로드 디렉토리 생성
            }

            // 파일 복사
            Files.copy(file.getInputStream(), targetLocation);

            // 프로필 이미지 경로 업데이트
            User user = userOpt.get();
            user.setProfile("/uploads/profiles/" + fileName);

            // DB에 업데이트
            userRepository.updateUser(userId, user.getPassword(), user.getIntroduction(), user.getProfile());
            return true;
        }

        return false;
    }
}
