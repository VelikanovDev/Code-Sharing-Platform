package com.velikanovdev.platform.service.impl;

import com.velikanovdev.platform.entity.Comment;
import com.velikanovdev.platform.exception.AppException;
import com.velikanovdev.platform.repository.CommentRepository;
import com.velikanovdev.platform.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.findById(id)
                .orElseThrow(() -> new AppException("Comment not found with ID: " + id, HttpStatus.BAD_REQUEST));

        commentRepository.deleteById(id);
    }
}
