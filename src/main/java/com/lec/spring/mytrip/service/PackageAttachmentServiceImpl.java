package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.PackagePost;
import com.lec.spring.mytrip.domain.attachment.PackagePostAttachment;
import com.lec.spring.mytrip.repository.PackageAttachmentRepository;
import com.nimbusds.openid.connect.sdk.assurance.evidences.attachment.Attachment;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
        // 업로드 디렉토리 경로 설정
        File uploadDir = new File("upload");  // 'upload' 디렉토리를 루트로 설정

        // 디렉토리가 존재하지 않으면 생성
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();  // 'upload' 디렉토리 생성
        }

        // 파일 저장 실패 시 DB 레코드 롤백을 위한 변수
        List<PackagePostAttachment> savedAttachments = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                // 파일 이름 생성: 사용자 ID + 원본 파일 이름
                String fileName = packagePost.getUser().getId() + "_" + file.getOriginalFilename();

                // DB에 첨부파일 정보 저장 (파일 이름만 저장)
                PackagePostAttachment packagePostAttachment = new PackagePostAttachment();
                packagePostAttachment.setPackageId(packagePost.getPackageId());
                packagePostAttachment.setFileName(fileName);

                // DB에 첨부파일 저장
                packageAttachmentRepository.insertAttachment(packagePostAttachment);

                // DB에 성공적으로 저장된 첨부파일을 리스트에 추가
                savedAttachments.add(packagePostAttachment);

                // 파일을 저장할 경로 지정 (업로드 디렉토리 아래에 파일 저장)
                Path filePath = Paths.get(uploadDir.getPath(), fileName);

                try {
                    // 파일 저장
                    file.transferTo(filePath.toFile());
                } catch (IOException e) {
                    // 파일 저장 실패 시 DB에서 해당 첨부파일 레코드 삭제
                    for (PackagePostAttachment attachment : savedAttachments) {
                        packageAttachmentRepository.deleteAttachment(attachment.getPackageAttachmentId());
                    }

                    // 트랜잭션 롤백을 위해 예외 발생
                    throw new RuntimeException("파일 저장에 실패하였습니다. DB 레코드를 롤백합니다.");
                }
            }
        }

        return 1;  // 파일 저장 성공 시 1 반환
    }

    //첨부파일 불러오기
    @Override
    public List<Attachment> getAttachmentsByPostId(int postId) {
        return List.of();
    }

    //첨부파일 삭제
    @Override
    public void deleteAttachment(int attachmentId) {
        // 삭제 로직
    }

    //파일 이름 중복 처리
    @Override
    public String generateUniqueFileName(String originalFileName) {
        // 이름 중복 처리 로직
        return originalFileName;
    }

    //파일 형식 및 크기 검증
    @Override
    public boolean isValidFile(MultipartFile file) {
        // 파일 검증 로직
        return true;
    }
}

