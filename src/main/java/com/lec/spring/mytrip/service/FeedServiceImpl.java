package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.PostAttachment;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.FeedRepository;
import com.lec.spring.mytrip.repository.LikeRepository;
import com.lec.spring.mytrip.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.lec.spring.mytrip.util.U;

@Service
public class FeedServiceImpl implements FeedService{

    private final FeedRepository feedRepository;
    private final LikeRepository likeRepository;

    public FeedServiceImpl(FeedRepository feedRepository, SqlSession sqlSession) {
        this.feedRepository = feedRepository;
        this.likeRepository = sqlSession.getMapper(LikeRepository.class);
    }

    @Override
    public List<Feed> getFeedByUserId(int userId) {
        System.out.println("DB 조회 결과: " + feedRepository.findFeedByUserId(userId));  // 로그 추가
        return feedRepository.findFeedByUserId(userId);
    }

    public List<Feed> findRecentFeedsByUserId(int userId) {
        return feedRepository.findRecentFeedsByUserId(userId);
    }

    @Override
    @Transactional
    public void insertFeed(Feed feed, List<MultipartFile> files) throws IOException {

        System.out.println("Before Insert boardId: " + feed.getBoardId());
        System.out.println("Before Insert view: " + feed.getBoardViewCount());
        // 게시물 삽입
        feedRepository.insertFeed(feed);  // 게시글이 DB에 저장된 후 boardId가 feed에 설정됨
        System.out.println("After Insert boardId: " + feed.getBoardId());

        // 파일이 첨부되었는지 확인
        if (files != null && !files.isEmpty()) {
            String projectDirectory = System.getProperty("user.dir");  // 프로젝트 루트 경로
            String uploadDirectory = projectDirectory + "\\uploads";  // uploads 폴더 경로
            String staticUploadDirectory = projectDirectory + "/src/main/resources/static/uploads"; // static 폴더 아래


            // 디렉토리가 존재하지 않으면 생성
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path staticUploadPath = Paths.get(staticUploadDirectory);
            if (!Files.exists(staticUploadPath)) {
                Files.createDirectories(staticUploadPath);
            }

            // 파일 첨부 객체 리스트 생성
            List<PostAttachment> attachments = new ArrayList<>();

            // 파일 하나씩 처리
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // 파일 이름을 얻기
                    String fileName = file.getOriginalFilename();
                    // 안전한 파일 이름 생성 (중복 방지, 특수 문자 제거 등)
                    String sanitizedFileName = fileName.replaceAll("[^a-zA-Z0-9.]", "_");
                    Path filePath = Paths.get(uploadDirectory, sanitizedFileName);

                    // 파일을 uploads 폴더에 저장
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    // 파일을 static/uploads 폴더에 복사
                    Path staticFilePath = Paths.get(staticUploadDirectory, sanitizedFileName);
                    Files.copy(file.getInputStream(), staticFilePath, StandardCopyOption.REPLACE_EXISTING);

                    // 첨부파일 객체 생성
                    PostAttachment attachment = PostAttachment.builder()
                            .boardId(feed.getBoardId()) // 게시물 ID 사용
                            .fileName(sanitizedFileName)
                            .filePath(staticFilePath.toString()) // static 폴더로 복사한 경로
                            .build();
                    attachments.add(attachment);
                    System.out.println("첨부파일 boardId: " + attachment.getBoardAttachmentId());
                }
            }

            // 첨부파일 DB 저장
            for (PostAttachment attachment : attachments) {
                feedRepository.insertAttachments(attachment); // 첨부파일 DB에 저장
            }
        }
    }
    @Override
    public void updateFeed(int boardId, int userId, String boardSubject, String boardContent, int cityId, List<MultipartFile> files) throws IOException {
        // Feed 객체 생성 (제목, 내용, 도시명 등)
        Feed feed = new Feed();
        feed.setBoardId(boardId);
        feed.setUserId(userId);
        feed.setBoardSubject(boardSubject);
        feed.setBoardContent(boardContent);
        feed.setCityId(cityId);

        System.out.println("updateFeed in service : " + feed.toString());

        // 게시물 수정: 제목, 내용, 도시명 수정
        feedRepository.updateFeed(feed);

        // 기존 첨부파일 삭제
        feedRepository.deleteAttachmentsByBoardId(boardId);

        if (files != null && !files.isEmpty()) {
            String projectDirectory = System.getProperty("user.dir");
            String uploadDirectory = projectDirectory + "/uploads"; // uploads 폴더 경로
            String staticUploadDirectory = projectDirectory + "/src/main/resources/static/uploads"; // static 폴더 경로

            // 디렉토리가 존재하지 않으면 생성
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path staticUploadPath = Paths.get(staticUploadDirectory);
            if (!Files.exists(staticUploadPath)) {
                Files.createDirectories(staticUploadPath);
            }

            // 파일 첨부 객체 리스트 생성
            List<PostAttachment> attachments = new ArrayList<>();

            // 파일 하나씩 처리
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // 파일 이름을 얻기
                    String fileName = file.getOriginalFilename();
                    // 안전한 파일 이름 생성 (중복 방지, 특수 문자 제거 등)
                    String sanitizedFileName = fileName.replaceAll("[^a-zA-Z0-9.]", "_");
                    Path filePath = Paths.get(uploadDirectory, sanitizedFileName);

                    // 파일을 uploads 폴더에 저장
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    // 파일을 static/uploads 폴더에 복사
                    Path staticFilePath = Paths.get(staticUploadDirectory, sanitizedFileName);
                    Files.copy(file.getInputStream(), staticFilePath, StandardCopyOption.REPLACE_EXISTING);

                    // 첨부파일 객체 생성
                    PostAttachment attachment = PostAttachment.builder()
                            .boardId(feed.getBoardId()) // 게시물 ID 사용
                            .fileName(sanitizedFileName)
                            .filePath(staticFilePath.toString()) // static 폴더로 복사한 경로
                            .build();
                    attachments.add(attachment);
                    System.out.println("첨부파일 boardId: " + attachment.getBoardAttachmentId());
                }
            }

            // 첨부파일 DB 저장
            for (PostAttachment attachment : attachments) {
                feedRepository.insertAttachments(attachment); // 첨부파일 DB에 저장
            }
        }
    }


    @Override
    public void deleteFeed(int boardId, int userId) {
        Feed existingFeed = feedRepository.findFeedByUserId(userId)
                .stream()
                .filter(f -> f.getBoardId() == boardId)
                .findFirst()
                .orElse(null);

        if (existingFeed != null) {
            feedRepository.deleteFeed(boardId, userId);
        } else {
            throw new IllegalArgumentException("삭제할 수 없는 게시물입니다.");
        }
    }


    //도시 별, 그리고 카테고리 별 불러오기
    @Override
    public List<Feed> findByCityAndCategory(int cityId, String category) {
//        System.out.println("도시 id : " + cityId + "  카테고리 : " + category);

        List<Feed> c = feedRepository.findByCityAndCategory(cityId, category);
//        System.out.println("서비스단 받아온 피드 : " + c.toString());
        c.forEach(feed -> {
//            System.out.println("이건 null일까?" + feed.getAttachmentFiles());
//            if(feed.getAttachments() != null){
//                feed.getAttachments().forEach(attachment -> {
//                    System.out.println("이것이 당신의 파일 이름" + attachment.getFileName());
//                });
//            }
            feed.setBoardLiked(likeRepository.getPostLikeCount(feed.getBoardId()));
        });

        return c;
    }

    @Override
    @Transactional
    public Feed detail(int groupId) {
        Feed feed = feedRepository.findById(groupId);
        System.out.println(feed.toString());

        if (feed != null) {
            feedRepository.addViewCnt(groupId);
            return feed;
        }
        return null;
    }

    //피드가 아닌 게시글도 삭제. 매퍼에 문자 박아넣지 마요오오오 나 울거가태ㅐㅐㅐㅐ
    @Override
    public int deleteGroup(int boardId){

        System.out.println("이거 로긘" + U.getLoggedUser().getId());
        System.out.println("이거 게시글 쓴 인간" + feedRepository.findById(boardId).getUserId());

        if(U.getLoggedUser().getId() == feedRepository.findById(boardId).getUserId()){
            feedRepository.deleteAttachmentsByBoardId(boardId);
            feedRepository.deleteFeed(boardId, U.getLoggedUser().getId());
            System.out.println("성공했는데");
            return 1;
        }else {
            return 0;
        }
    }

    //파일 이름 중복 처리
    @Override
    public String generateUniqueFileName(String originalFileName, int userId) {
        try {
            // 한글 파일 이름 UTF-8 인코딩
            String encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8.toString());
            encodedFileName = encodedFileName.replace("+", "%20"); // 공백 처리

            // 파일 이름 생성
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String baseFileName
                    = userId + "_" + timeStamp + "_" + encodedFileName.replaceAll("[^a-zA-Z0-9._-]", "_");
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

}
