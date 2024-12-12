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
import java.util.List;
import java.util.Map;

@Service
public class FeedServiceImpl implements FeedService {

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
    public void insertFeed(Feed feed) {
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
