package com.lec.spring.mytrip.service;

import com.lec.spring.mytrip.domain.Comment;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.repository.CommentRepository;
import com.lec.spring.mytrip.util.U;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

 public CommentServiceImpl(SqlSession sqlSession) {
     this.repository = sqlSession.getMapper(CommentRepository.class);
 }

    @Override
    public int writeComment(Comment comment) {
//       comment.setUserId(U.getLoggedUser().getId());
        comment.setUserId(1);
        return repository.insertComment(comment);
    }

    @Override
    public List<Comment> getComments(int boardId) {
        List<Comment> commentsByBoardId = repository.findCommentsByBoardId(boardId);
        System.out.println("commentsByBoardId: " + commentsByBoardId);

        return commentsByBoardId;
    }



    @Override
    public int deleteComment(int commentId) {
        return repository.deleteComment(commentId);
    }
}
