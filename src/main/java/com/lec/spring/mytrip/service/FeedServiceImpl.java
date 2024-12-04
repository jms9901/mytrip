package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Feed;
import com.lec.spring.mytrip.domain.PostAttachment;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.FeedRepository;
import com.lec.spring.mytrip.repository.PostAttachmentRepository;
import com.lec.spring.mytrip.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final PostAttachmentRepository postAttachmentRepository;

    public FeedServiceImpl(SqlSession sqlSession) {
        feedRepository = sqlSession.getMapper(FeedRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
        postAttachmentRepository = sqlSession.getMapper(PostAttachmentRepository.class);
        System.out.println("FeedService() 생성");
    }


    // 작성
    // postAttachment 다 만들고 수정
    @Override
    public int write(Feed feed, Map<String, MultipartFile> files) {
        // 새 피드 저장
        int result = feedRepository.save(feed);

        // 첨부파일 저장
//        if(files != null && !files.isEmpty()) {
//            for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
//                MultipartFile file = entry.getValue();
//
//                if(!file.isEmpty()) {
//                    // 저장 디렉토리 생성
//                    String savePath = U.getUploardDir() + File.separator + "feed" + File.separator + feed.getId();
//                    File dir = new File(savePath);
//                    if(!dir.exists()) {
//                        dir.mkdirs();   // 디렉토리 생성
//                    }
//
//                    // 파일 저장
//                    String fileName = feed.getCategory() + feed.getUserId() + "_" + file.getOriginalFilename();
//                    File saveFile = new File(savePath + File.separator + fileName);
//
//                    try {
//                        file.transferTo(saveFile);  // 파일 저장
//                        PostAttachment attachment = new PostAttachment(feed.getId(), fileName);
//                        postAttachmentRepository.save(attachment); // 첨부파일 DB 저장
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        throw new RuntimeException("첨부파일 저장 중 오류 발생: " + e.getMessage());
//                    }
//                }
//
//            }
//        }
        return 0;
    }


    // 물리적 서버 파일에 저장
    // 파일 이름
    // 소모임 : Board_userId_원본파일명
    // 피드 : Feed_userId_원본파일명

    @Override
    @Transactional
    public Feed detail(Long id) {
        feedRepository.viewCnt(id); // 조회수 증가
        Feed feed = feedRepository.findById(id);

        return feed;
    }


    @Override
    public List<Feed> list() {
        return feedRepository.findAll();
    }

    @Override
    public Feed findById(Long id) {
        Feed feed = feedRepository.findById(id);
        return feed;
    }

    // 수정
    // postAttachment 다 만들고 수정
    @Override
    public int update(Feed feed
            , Map<String, MultipartFile> files
            , Long[] delfile
    ) {
        int result = feedRepository.update(feed);

        // 삭제할 파일 처리
//        if(delfile != null && delfile.length > 0) {
//            for(Long fileId : delfile) {
//                PostAttachment attachment = postAttachmentRepository.findById(fileId);
//                if(attachment != null) {
//                    // 파일 삭제
//                    String savePath = U.getUploardDir() + File.separator + "feed" + File.separator + feed.getId();
//                    File file = new File(savePath + File.separator + fileId);
//                    if(file.exists()) {
//                        file.delete();
//                    }
//
//                    // DB에서 삭제
//                    postAttachmentRepository.delete(fileId);
//                }
//            }
//        }

        // 새로 추가된 파일 저장
//        if(files != null && !files.isEmpty()) {
//            for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
//                MultipartFile file = entry.getValue();
//
//                if(!file.isEmpty()) {
//                    // 저장 디렉토리 생성
//                    String savePath = U.getUploardDir() + File.separator + "feed" + File.separator + feed.getId();
//                    File dir = new File(savePath);
//                    if(!dir.exists()) {
//                        dir.mkdirs();   // 디렉토리 생성
//                    }
//
//                    // 파일 저장
//                    String fileName = feed.getCategory() + feed.getUserId() + "_" + file.getOriginalFilename();
//                    File saveFile = new File(savePath + File.separator + fileName);
//
//                    try {
//                        file.transferTo(saveFile);  // 파일 저장
//                        PostAttachment attachment = new PostAttachment(feed.getId(), fileName);
//                        postAttachmentRepository.save(attachment); // 첨부파일 DB 저장
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        throw new RuntimeException("첨부파일 저장 중 오류 발생: " + e.getMessage());
//                    }
//                }
//
//            }
//        }

        return result;
    }

    // 파일 삭제
    // postAttachment 다 만들고 수정
    @Override
    public int deleteById(Long id) {

//        // 첨부파일 삭제
//        List<PostAttachment> attachments = postAttachmentRepository.findByFeedId(id);
//        if(attachments != null && !attachments.isEmpty()) {
//            for(PostAttachment attachment : attachments) {
//                // 파일 삭제
//                String savePath = U.getUploardDir() + File.separator + "feed" + File.separator + id;
//                File file = new File(savePath, attachment.getFileName());
//                if (file.exists()) {
//                    file.delete();
//                }
//            }
//        }
//
//        // DB 첨부 파일 삭제
//        postAttachmentRepository.deleteByFeedId(id);
//
//        // 피드 삭제
//        return feedRepository.deleteById(id);
        return 0;
    }
}
