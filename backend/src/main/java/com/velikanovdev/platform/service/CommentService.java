package com.velikanovdev.platform.service;

import com.velikanovdev.platform.entity.Comment;

public interface CommentService {
    Comment addComment(Comment comment);
    void deleteComment(Long id);
}
