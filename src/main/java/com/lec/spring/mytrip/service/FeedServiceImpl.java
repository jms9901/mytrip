package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.PostAttachment;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.domain.attachment.BoardAttachment;
import com.lec.spring.mytrip.domain.attachment.PackagePostAttachment;
import com.lec.spring.mytrip.repository.FeedRepository;
import com.lec.spring.mytrip.repository.UserRepository;
import com.lec.spring.mytrip.util.U;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FeedServiceImpl implements FeedService{

    private final PackageAttachmentService packageAttachmentService;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    public FeedServiceImpl(SqlSession sqlSession, PackageAttachmentService packageAttachmentService) {
        this.packageAttachmentService = packageAttachmentService;
        feedRepository = sqlSession.getMapper(FeedRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
        System.out.println("FeedService() 생성");
    }
//    public FeedServiceImpl(FeedRepository feedRepository, UserRepository userRepository) {
//        this.feedRepository = feedRepository;
//        this.userRepository = userRepository;
//    }



    // 작성
    // postAttachment 다 만들고 수정
    @Override
    @Transactional
    public boolean write(Feed feed, List<MultipartFile> files) {

//        int userId = U.getLoggedUser().getId();

        int userId = 1;

        feed.setUserId(userId);

        // 새 피드 저장
        int result = feedRepository.save(feed);

        // 첨부파일 저장
        if (result > 0 && files != null) {
            packageAttachmentService.savePostAttachments(files, feed);
        }
        return result > 0;
    }

    public void saveAttachment(Feed feed, Map<String, MultipartFile> files) {
        User user = new User();

        String UPLOAD_DIR = "upload";

        for (MultipartFile file : files.values()) {
            if(!file.isEmpty()) {
                try{
                    // 업로드 파일 만들기
                    String categoryDir = UPLOAD_DIR + File.separator + feed.getBoardCategory();
                    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                    File uploadDir = new File(categoryDir);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    String boardAttachmentFile = user.getId() + "_" + file.getOriginalFilename();

                    // 파일 저장 경로
                    Path filePath = Paths.get(uploadDir.getPath(), boardAttachmentFile);
                    Files.copy(file.getInputStream(), filePath);

                    // 첨부파일 생성
                    PostAttachment postAttachment = new PostAttachment();
                    postAttachment.setFeed(feed);
//                    postAttachment.setBoardAttachmentFile(boardAttachmentFile);
//                    postAttachment.setFilepath(filePath.toString());

                    // 첨부파일 정보 저장
                    feedRepository.saveAttachment(postAttachment);

                } catch (IOException e) {
                    throw new RuntimeException("파일 저장 실패", e);
                }
            }
        }
    }

    // 물리적 서버 파일에 저장
    // 파일 이름
    // 소모임 : Board_userId_원본파일명
    // 피드 : Feed_userId_원본파일명

    @Override
    @Transactional
    public Feed detail(int id) {
        // 본인이 누를 시 조회수 증가 X
        // 조회수 증가
//        feedRepository.viewCnt(id);
        Feed feed = feedRepository.findById(id);
        if (feed != null) {
            feed.setAttachments(feedRepository.findAttachmentByBoardId(id));
//            User user = U.getLoggedUser();
            User user = User.builder()
                    .id(1)
                    .name("이경원")
                    .email("wonwon123123@naver.com")
                    .build();
            feed.setUser(user);
        return feed;
        }
        return null;
    }
    @Override
    public Feed findById(int id) {
        Feed feed = feedRepository.findById(id);
        return feed;
    }

    @Override
    public List<Feed> getFeedByUserId(int userId) {
        System.out.println("DB 조회 결과: " + feedRepository.findFeedByUserId(userId));  // 로그 추가
        return feedRepository.findFeedByUserId(userId);
    }

    @Override
    public boolean update(Feed feed, List<MultipartFile> files) {

        // 기존 첨부파일 삭제
        List<PackagePostAttachment> attachments = packageAttachmentService.findByPackageId(feed.getBoardId());

        if(!attachments.isEmpty()) {
            attachments.forEach(e ->
                    packageAttachmentService.deleteBoardAttachment(e.getPackageAttachmentId()));
        }

        // 첨부파일 저장
        if (feedRepository.update(feed) > 0) {
            if(files != null) packageAttachmentService.savePostAttachments(files, feed);
            return true;
        }
        return false;
    }

    public List<Feed> findRecentFeedsByUserId(int userId) {
        return feedRepository.findRecentFeedsByUserId(userId);
    }

    // 수정
    // postAttachment 다 만들고 수정
    @Override
    @Transactional
    public void insertFeed(Feed feed, List<MultipartFile> files) throws IOException {
        feed.setBoardCategory("피드");

        System.out.println("Before Insert boardId: " + feed.getBoardId());
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
    public boolean deleteById(int id) {
        Feed feed = new Feed();

        // 첨부파일 삭제
        List<PackagePostAttachment> attachments = packageAttachmentService.findByPackageId(id);

        if(!attachments.isEmpty()) {
            attachments.forEach(e ->
                    packageAttachmentService.deleteBoardAttachment(e.getPackageAttachmentId()));
        }

        // 피드 삭제
        return feedRepository.delete(id) > 0;
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

    // 도시 불러오기
    @Override
    public List<City> getAllCities() {
        return feedRepository.findAllCities();
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
        System.out.println("도시 id : " + cityId + "  카테고리 : " + category);

        List<Feed> c = feedRepository.findByCityAndCategory(cityId, category);
//        System.out.println("서비스단 받아온 피드 : " + c.toString());
        return c;
    }

}
