package com.velikanovdev.platform.service;

import com.velikanovdev.platform.dto.CommentRequestDto;
import com.velikanovdev.platform.dto.CommentResponseDto;

public interface CommentService {
    CommentResponseDto addComment(CommentRequestDto comment);
    CommentResponseDto deleteComment(Long id);
}
