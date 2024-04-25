package com.velikanovdev.platform.controller;

import com.velikanovdev.platform.entity.Comment;
import com.velikanovdev.platform.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(path = "/add/{id}", produces = "application/json")
    public ResponseEntity<Comment> addComment(@PathVariable Long id, @RequestBody Comment comment) {
        log.info("Adding comment to the snippet with id " + id);
        Comment addedComment = commentService.addComment(id, comment);
        return ResponseEntity.ok(addedComment);
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        log.info("Deleting comment with id " + id);
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment has been deleted");
    }
}
