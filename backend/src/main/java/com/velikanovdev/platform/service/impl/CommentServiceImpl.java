package com.velikanovdev.platform.service.impl;

import com.velikanovdev.platform.dto.CommentRequestDto;
import com.velikanovdev.platform.dto.CommentResponseDto;
import com.velikanovdev.platform.entity.Comment;
import com.velikanovdev.platform.entity.Snippet;
import com.velikanovdev.platform.exception.AppException;
import com.velikanovdev.platform.mappers.EntityDtoMapper;
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

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, SnippetRepository snippetRepository) {
        this.commentRepository = commentRepository;
        this.snippetRepository = snippetRepository;
    }

    @Override
    public CommentResponseDto addComment(CommentRequestDto commentDto) {
        Snippet snippet = snippetRepository.findById(commentDto.snippetId())
                .orElseThrow(() -> new AppException("Snippet not found with ID: " + commentDto.snippetId(), HttpStatus.NOT_FOUND));

        Comment commentToSave = new Comment();
        commentToSave.setText(commentDto.text());
        commentToSave.setDate(LocalDateTime.now());
        commentToSave.setSnippet(snippet);
        commentToSave.setUsername(commentDto.username());
        return EntityDtoMapper.INSTANCE.toCommentResponseDto(commentRepository.save(commentToSave));
    }

    @Override
    public CommentResponseDto deleteComment(Long id) {
        Comment commentToDelete = commentRepository.findById(id)
                .orElseThrow(() -> new AppException("Comment not found with ID: " + id, HttpStatus.BAD_REQUEST));

        commentRepository.deleteById(id);

        return EntityDtoMapper.INSTANCE.toCommentResponseDto(commentToDelete);
    }
}
