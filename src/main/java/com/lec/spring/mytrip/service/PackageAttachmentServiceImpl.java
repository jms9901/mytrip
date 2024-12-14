package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.attachment.BoardAttachment;
import com.lec.spring.mytrip.domain.attachment.PackagePostAttachment;
import com.lec.spring.mytrip.repository.PackageAttachmentRepository;
import com.nimbusds.openid.connect.sdk.assurance.evidences.attachment.Attachment;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PackageAttachmentServiceImpl implements PackageAttachmentService {

    private final PackageAttachmentRepository packageAttachmentRepository;

    public PackageAttachmentServiceImpl(SqlSession sqlSession) {
        this.packageAttachmentRepository = sqlSession.getMapper(PackageAttachmentRepository.class);
    }

    // 첨부파일 저장
    @Override
    @Transactional
    public int saveAttachments(List<MultipartFile> files, PackagePost packagePost) {
        // 절대 경로로 설정하기, 시스템의 특정 디렉토리에서 생성
        String uploadPath = System.getProperty("user.dir") + File.separator + "uploads/package"; // 현재 작업 디렉토리 기준

        File uploadDir = new File(uploadPath);
        System.out.println("첨부파일 저장 디렉토리: " + uploadPath); // 경로 출력

        String fileName = ""; //파일 이름

        // 디렉토리가 존재하지 않으면 생성
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();  // 'upload' 디렉토리 생성
            if (created) {
                System.out.println("디렉토리가 성공적으로 생성되었습니다.");
            } else {
                System.out.println("디렉토리 생성에 실패하였습니다.");
            }
        } else {
            System.out.println("디렉토리가 이미 존재합니다.");
        }

        // 파일 저장 실패 시 DB 레코드 롤백을 위한 변수
        List<PackagePostAttachment> savedAttachments = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    // 1. 파일 검증
                    if (!isValidFile(file)) {
                        throw new IllegalArgumentException("유효하지 않은 파일입니다: " + file.getOriginalFilename());
                    }

                    // 2. 파일 이름 생성
                    fileName = generateUniqueFileName(file.getOriginalFilename(), packagePost.getUser().getId());

                    // 3. DB에 첨부파일 정보 저장
                    PackagePostAttachment packagePostAttachment = new PackagePostAttachment();
                    packagePostAttachment.setPackageId(packagePost.getPackageId());
                    packagePostAttachment.setFileName(fileName);
                    packageAttachmentRepository.insertAttachment(packagePostAttachment);
                    savedAttachments.add(packagePostAttachment);

                    // 4. 파일 저장
                    Path filePath = Paths.get(uploadDir.getPath(), fileName);
                    file.transferTo(filePath.toFile());

                } catch (IOException e) {
                    // 파일 저장 실패 시 DB에서 해당 첨부파일 레코드 삭제
                    for (PackagePostAttachment attachment : savedAttachments) {
                        packageAttachmentRepository.deleteAttachment(attachment.getPackageAttachmentId());
                    }

                    // 트랜잭션 롤백을 위해 예외 발생
                    throw new RuntimeException("파일 저장에 실패하였습니다. DB 레코드를 롤백합니다.", e);

                } catch (IllegalArgumentException e) {
                    // 유효하지 않은 파일에 대한 예외 처리
                    System.err.println("파일 검증 실패: " + e.getMessage());
                    throw e; // 필요한 경우 재처리 로직 추가 가능
                }
            }
        }

        return 1;
    }


    //첨부파일 불러오기
    @Override
    public List<PackagePostAttachment> getAttachmentsByPostId(int postId) {
        return packageAttachmentRepository.findByPackageId(postId);
    }

    //첨부파일 삭제
    @Override
    public void deleteAttachment(int attachmentId) {
        // 삭제 로직
    }

    //파일 이름 중복 처리
    @Override
    public String generateUniqueFileName(String originalFileName, int userId) {
        try {
            // 한글 파일 이름 UTF-8 인코딩
            String encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8.toString());
            encodedFileName = encodedFileName.replace("+", "%20"); // 공백 처리

            // 파일 이름 생성
            String baseFileName = userId + "_" + encodedFileName.replaceAll("[^a-zA-Z0-9._-]", "_");
            File uploadDir = new File("upload");

            String uniqueFileName = baseFileName;
            int duplicateCount = 1;

            // 중복 파일 이름 처리
            while (new File(uploadDir, uniqueFileName).exists()) {
                int dotIndex = baseFileName.lastIndexOf(".");
                if (dotIndex > 0) {
                    String nameWithoutExtension = baseFileName.substring(0, dotIndex);
                    String extension = baseFileName.substring(dotIndex);
                    uniqueFileName = nameWithoutExtension + "_" + duplicateCount + extension;
                } else {
                    uniqueFileName = baseFileName + "_" + duplicateCount;
                }
                duplicateCount++;
            }

            return uniqueFileName;

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("파일 이름 인코딩에 실패했습니다.", e);
        }
    }



    //파일 형식 및 크기 검증
    @Override
    public boolean isValidFile(MultipartFile file) {
        if (file.isEmpty()) return false;

        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "pdf");
        long maxFileSize = 10 * 1024 * 1024;

        if (file.getSize() > maxFileSize) return false;

        String originalFileName = file.getOriginalFilename();
        if (originalFileName != null) {
            int dotIndex = originalFileName.lastIndexOf(".");
            if (dotIndex > 0 && dotIndex < originalFileName.length() - 1) {
                String fileExtension = originalFileName.substring(dotIndex + 1).toLowerCase();
                return allowedExtensions.contains(fileExtension);
            }
        }

        return false;
    }


}

