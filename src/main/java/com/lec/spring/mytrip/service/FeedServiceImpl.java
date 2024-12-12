package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.PostAttachment;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.FeedRepository;
import com.lec.spring.mytrip.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FeedServiceImpl implements FeedService{

    private final FeedRepository feedRepository;

    public FeedServiceImpl(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public List<Feed> getFeedByUserId(int userId) {
        System.out.println("DB 조회 결과: " + feedRepository.findFeedByUserId(userId));  // 로그 추가
        return feedRepository.findFeedByUserId(userId);
    }

    @Override
    public void insertFeed(Feed feed,List<MultipartFile> files) throws IOException {
        feed.setBoardCategory("피드");
        // 파일이 첨부되었는지 확인
        if (files != null && !files.isEmpty()) {
            String projectDirectory = System.getProperty("user.dir");  // 프로젝트 루트 경로
            String uploadDirectory = projectDirectory + "\\uploads";  // uploads 폴더 경로

            // 디렉토리가 존재하지 않으면 생성
            Path path = Paths.get(uploadDirectory);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
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

                    // 첨부파일 객체 생성
                    PostAttachment attachment = new PostAttachment();
                    attachment.setFileName(sanitizedFileName);  // 파일 이름만 저장
                    attachment.setFilePath(uploadDirectory + "\\" + sanitizedFileName);  // 경로 포함하여 저장

                    attachments.add(attachment);

                    // 파일을 서버에 저장
                    Path filePath = Paths.get(uploadDirectory, sanitizedFileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);  // 기존 파일 덮어쓰기
                }
            }

            // 첨부파일 목록을 Feed 객체에 설정
            feed.setAttachments(attachments);
        }

        // 피드 저장
        feedRepository.insertFeed(feed);
    }
    @Override
    public void updateFeed(Feed feed) {
        Feed existingFeed = feedRepository.findFeedByUserId(feed.getUserId())
                .stream()
                .filter(f -> f.getBoardId() == feed.getBoardId())
                .findFirst()
                .orElse(null);
        if (existingFeed != null) {
            feedRepository.updateFeed(feed);
        }else {
            throw new IllegalArgumentException("수정 불가");
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
}
