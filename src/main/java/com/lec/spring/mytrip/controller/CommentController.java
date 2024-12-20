package com.lec.spring.mytrip.controller;

import com.lec.spring.mytrip.domain.Comment;
import com.lec.spring.mytrip.domain.User;
import com.lec.spring.mytrip.service.CommentService;


import com.lec.spring.mytrip.util.U;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> getComments(@PathVariable int boardId) {
        // 댓글 목록 조회
        List<Comment> comments = commentService.getComments(boardId);
        // 게시글 작성자 ID 조회
        int boardWriterId = commentService.getBoardWriterId(boardId);

        // 로그인한 사용자 ID 조회
        User loggedUser = U.getLoggedUser();
        int loggedInUserId = (loggedUser != null) ? loggedUser.getId() : -1; // 로그인하지 않았으면 -1 반환

        // Map에 담아서 반환
        Map<String, Object> response = new HashMap<>();
        response.put("comments", comments);
        response.put("boardWriterId", boardWriterId);
        response.put("loggedInUserId", loggedInUserId); // 로그인된 사용자 ID 추가

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable int commentId) {
        System.out.println("댓글 컨트롤러 삭제");

        commentService.deleteComment(commentId);
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }

}
