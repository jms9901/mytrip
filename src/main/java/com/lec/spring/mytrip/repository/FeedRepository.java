package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedRepository {
    // 새 피드 작성 INSERT
    int save(Feed feed);

    // 특정 피드 id 글 내용 읽기
    // 상세보기
    Feed findById(int boardId);

    // 특정 사용자의 피드 읽기
    // 본인이 작성한 피드 리스트 불러오기
    List<Feed> findByUserId(int userId);

    // 특정 id 글 조회수 증가
//    int viewCnt(int id);

    // 전체 글 목록 : 최신순
//    List<Feed> findAll();

    // 가장 최근 게시물 9개 개인정보 오른쪽 페이지에 배치
//    int findRecentFeedByUser(int id);

    // 특정 피드 id 피드 수정 -> 모달에서 처리
    int update(Feed feed);

    // 특정 피드 id 피드 삭제 -> 모달에서 처리
    int delete(int id);

    // 전체 피드 개수
//    int feedCountAll();

    // 첨부파일 저장
    int saveAttachment(PostAttachment attachment);

    // 특정 피드 ID로 첨부파일 찾기
    List<PostAttachment> findAttachmentByBoardId(int boardId);

    // 첨부파일 삭제
    int deleteAttachment(int boardAttachmentId);

    // 특정 피드 ID의 첨부파일 삭제
    int deleteAttachmentByBoardId(int boardId);

    // 도시
    List<City> findAllCities();

    // 특정 피드 ID로 CITY 찾기
    List<City> findCityById(int boardId);

//    private int boardId;                       // 게시물 ID
//    private User user;
//    private City city;
//    private String boardSubject;                // 게시물 제목
//    private String boardContent;                // 게시물 내용
//    private int boardViewCount;                // 게시물 조회수
//    private LocalDateTime boardDate;            // 게시물 작성일.
//    private BoardCategory boardCategory;        // 게시물 카테고리 (피드, 소모임 => Enum 타입)
//    private List<PostAttachment> attachments;   // 첨부파일
//
//    // 파일 업로드를 위한 임시 필드
//    private transient List<MultipartFile> uploadFiles;
//    private transient Long[] deleteFileIds;

}
