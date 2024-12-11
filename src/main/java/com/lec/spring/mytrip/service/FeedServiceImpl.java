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
    public int write(Feed feed, Map<String, MultipartFile> files) {
        // 현재 로그인한 정보 확인
        User user = U.getLoggedUser();

        if (user != null) {
            user = userRepository.findByUsername(user.getUsername());
        }
        feed.setUser(user);

        // 새 피드 저장
        int result = feedRepository.save(feed);

        // 첨부파일 저장
        saveAttachment(feed, files);

        return result;
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


    @Override
    @Transactional
    public Feed detail(Long boardId) {
        // 본인이 누를 시 조회수 증가 X
        // 조회수 증가
//        feedRepository.viewCnt(id);
        Feed feed = feedRepository.findById(boardId);
        if (feed != null) {
            feed.setAttachments(feedRepository.findAttachmentByBoardId(boardId));
        }
        return feed;
    }


//    @Override
//    public List<Feed> list() {
//        // 전체 리스트 보여주기
//        List<Feed> feeds = feedRepository.findAll();
//        return feeds;
//    }

    @Override
    public List<Feed> listByUser(Long userId) {
        // 본인이 작성한 피드 리스트만 보여주기
        return feedRepository.findByUserId(userId);
    }

    // 수정
    // postAttachment 다 만들고 수정
    @Override
    @Transactional
    public boolean update(Feed feed
            , Map<String, MultipartFile> files
            , Long[] delFileIds
    ) {
        // 지정된 첨부파일 삭제
        if(delFileIds != null) {
            for (Long boardAttachmentId : delFileIds) {
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
    public boolean deleteById(Long boardId) {
        Feed feed = new Feed();
        // 첨부파일 삭제
        feedRepository.deleteAttachmentByBoardId(boardId);

        // 피드 삭제
        return feedRepository.delete(boardId) > 0;
    }

    // 도시 불러오기
    @Override
    public List<City> getAllCities() {
        return feedRepository.findAllCities();
    }

}
