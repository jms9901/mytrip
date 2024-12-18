package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Comment;

import java.util.List;

public interface CommentService {
    int writeComment(Comment comment);
    List<Comment> getComments(int boardId);
    // 게시글 작성자 ID 조회
    int getBoardWriterId(int boardId); // 추가된 메서드
    int deleteComment(int commentId);
}
