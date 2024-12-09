package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.PostAttachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostAttachmentRepository {
    // 첨부파일 등록
    int save(PostAttachment attachment);

    // 특정 피드의 첨부파일 목록 조회
    List<PostAttachment> findAttachmentsByBoardId(@Param("boardId") Long boardId);

    // 특정 피드의 모든 첨부파일 삭제
    int deleteAttachmentsByBoardId(@Param("boardId") Long boardId);

    // 첨부파일 개별 삭제
    int deleteAttachmentById(@Param("attachmentId") Long attachmentId);
}
