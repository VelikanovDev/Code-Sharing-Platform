package com.velikanovdev.platform.service.impl;

import com.velikanovdev.platform.entity.Comment;
import com.velikanovdev.platform.entity.Snippet;
import com.velikanovdev.platform.exception.AppException;
import com.velikanovdev.platform.repository.CommentRepository;
import com.velikanovdev.platform.repository.SnippetRepository;
import com.velikanovdev.platform.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final SnippetRepository snippetRepository;

    public CommentServiceImpl(CommentRepository commentRepository, SnippetRepository snippetRepository) {
        this.commentRepository = commentRepository;
        this.snippetRepository = snippetRepository;
    }

    @Override
    public Comment addComment(Long snippetId, Comment comment) {
        Snippet snippet = snippetRepository.findById(snippetId)
                .orElseThrow(() -> new AppException("Snippet not found with ID: " + snippetId, HttpStatus.NOT_FOUND));

        comment.setDate(LocalDateTime.now());
        comment.setSnippet(snippet);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.findById(id)
                .orElseThrow(() -> new AppException("Comment not found with ID: " + id, HttpStatus.BAD_REQUEST));

        commentRepository.deleteById(id);
    }
}
