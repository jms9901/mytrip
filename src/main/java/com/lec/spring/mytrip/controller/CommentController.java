package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.Comment;
import com.lec.spring.mytrip.service.CommentService;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService service) {
        this.commentService = service;
    }
    @PostMapping("/write")
    public ResponseEntity<String> writeComment(@RequestBody Comment comment) {
        System.out.println("댓글 컨트롤러 작성");
        commentService.writeComment(comment);
        return ResponseEntity.ok("댓글이 작성되었습니다.");
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable int boardId) {
        return ResponseEntity.ok(commentService.getComments(boardId));
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable int commentId) {
        System.out.println("댓글 컨트롤러 삭제");

        commentService.deleteComment(commentId);
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }

}
