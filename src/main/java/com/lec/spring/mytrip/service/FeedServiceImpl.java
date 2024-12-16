package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.City;
import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.PostAttachment;
import com.lec.spring.mytrip.domain.User;
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
import java.util.List;
import java.util.Map;

@Service
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    public FeedServiceImpl(SqlSession sqlSession) {
        feedRepository = sqlSession.getMapper(FeedRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
        System.out.println("FeedService() 생성");
    }


    // 작성
    // postAttachment 다 만들고 수정
    @Override
    @Transactional
    public boolean write(Feed feed, Map<String, MultipartFile> files) {
        // 새 피드 저장
        int result = feedRepository.save(feed);

        // 첨부파일 저장
        if (result > 0 && files != null) {
            saveAttachment(feed, files);
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
                    postAttachment.setBoardAttachmentFile(boardAttachmentFile);
                    postAttachment.setFilepath(filePath.toString());

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
    public List<Feed> list() {
        List<Feed> feeds = feedRepository.findAll();
        for(Feed feed : feeds){
            feed.setAttachments(feedRepository.findAttachmentByBoardId(feed.getBoardId()));
        }
        return feeds;
    }

    @Override
    public Feed findById(int id) {
        Feed feed = feedRepository.findById(id);
        return feed;
    }

    // 수정
    // postAttachment 다 만들고 수정
    @Override
    @Transactional
    public boolean update(Feed feed
            , Map<String, MultipartFile> files
            , int[] delFileIds
    ) {
        // 지정된 첨부파일 삭제
        if(delFileIds != null) {
            for (int boardAttachmentId : delFileIds) {
                feedRepository.deleteAttachment(boardAttachmentId);
            }
        }

        // 피드 업데이트
        int result = feedRepository.update(feed);

        // 새 첨부파일 추가
        if(result > 0 && files != null) {
            saveAttachment(feed, files);
        }
        return result > 0;
    }

    // 파일 삭제
    // postAttachment 다 만들고 수정
    @Override
    public boolean deleteById(int id) {
        Feed feed = new Feed();
        // 첨부파일 삭제
        feedRepository.deleteAttachmentByBoardId(id);

        // 피드 삭제
        return feedRepository.delete(id) > 0;
    }

    // 도시 불러오기
    @Override
    public List<City> getAllCities() {
        return feedRepository.findAllCities();
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
