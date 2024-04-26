package com.velikanovdev.platform.controller;

import com.velikanovdev.platform.dto.CommentRequestDto;
import com.velikanovdev.platform.dto.CommentResponseDto;
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

    @PostMapping(path = "/add", produces = "application/json")
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentRequestDto comment) {
        log.info("Adding comment to the snippet with id " + comment.snippetId());
        CommentResponseDto addedComment = commentService.addComment(comment);
        return ResponseEntity.ok(addedComment);
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json")
    public ResponseEntity<CommentResponseDto> deleteComment(@PathVariable Long id) {
        log.info("Deleting comment with id " + id);
        CommentResponseDto deletedComment = commentService.deleteComment(id);
        return ResponseEntity.ok(deletedComment);
    }
}
