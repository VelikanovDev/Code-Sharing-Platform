package com.velikanovdev.platform.controller;

import com.velikanovdev.platform.entity.Comment;
import com.velikanovdev.platform.entity.Snippet;
import com.velikanovdev.platform.service.CommentService;
import com.velikanovdev.platform.service.PlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@Slf4j
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    private final PlatformService platformService;

    @Autowired
    public CommentController(CommentService commentService, PlatformService platformService) {
        this.commentService = commentService;
        this.platformService = platformService;
    }

    @PostMapping(path = "/add/{id}", produces = "application/json")
    public ResponseEntity<Comment> addComment(@PathVariable Long id, @RequestBody Comment comment) {
        log.info("Adding comment to the snippet with id " + id);

        Snippet snippet = platformService.getSnippet(id);

        if(snippet == null) {
            return ResponseEntity.notFound().build();
        }

        comment.setDate(LocalDateTime.now());
        comment.setSnippet(snippet);

        Comment addedComment = commentService.addComment(comment);

        return ResponseEntity.ok().body(addedComment);
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        log.info("Deleting comment with id " + id);
        commentService.deleteComment(id);

        return ResponseEntity.ok("Comment has been deleted");
    }
}
