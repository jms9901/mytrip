package com.lec.spring.mytrip.repository;

import com.lec.spring.mytrip.domain.Comment;

import java.util.List;

public interface CommentRepository {
    // 댓글 작성
    int insertComment(Comment comment);

    // 특정 게시물의 댓글 목록 조회
    List<Comment> findCommentsByBoardId(int boardId);



    // 댓글 삭제
    int deleteComment(int CommentId);
}
