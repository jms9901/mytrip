package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Comment;

import java.util.List;

public interface CommentService {
    int writeComment(Comment comment);
    List<Comment> getComments(int boardId);

    int deleteComment(int commentId);
}
