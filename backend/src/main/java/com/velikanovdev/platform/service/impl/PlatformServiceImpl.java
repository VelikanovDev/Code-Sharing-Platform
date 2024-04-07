package com.velikanovdev.platform.service.impl;

import com.velikanovdev.platform.entity.Comment;
import com.velikanovdev.platform.entity.Snippet;
import com.velikanovdev.platform.exception.AppException;
import com.velikanovdev.platform.repository.CommentRepository;
import com.velikanovdev.platform.repository.PlatformRepository;
import com.velikanovdev.platform.service.PlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlatformServiceImpl implements PlatformService {
    private final PlatformRepository platformRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public PlatformServiceImpl(PlatformRepository platformRepository, CommentRepository commentRepository) {
        this.platformRepository = platformRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Snippet getSnippet(Long id) {
        Optional<Snippet> optionalCodeEntity = platformRepository.findById(id);
        return optionalCodeEntity.orElse(null);
    }

    @Override
    public Long addOrUpdateSnippet(Snippet snippet) {
        if (snippet.getId() == null) {
            log.info("PlatformServiceImpl: before saving a new snippet");
            Snippet s = platformRepository.save(snippet);
            return s.getId();

        } else {
            log.info("PlatformServiceImpl: before updating a snippet");
            Snippet existingSnippet = platformRepository.findById(snippet.getId()).orElseThrow();
            snippet.setId(existingSnippet.getId());
            Snippet s = platformRepository.save(snippet);
            return s.getId();
        }

    }

    @Override
    public List<Snippet> getLatest() {
        return platformRepository.findAllByOrderByDateDesc()
                .stream()
                .sorted((c1, c2) -> c2.getDate().compareTo(c1.getDate()))
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSnippet(Long id) {
        platformRepository.findById(id)
                .orElseThrow(() -> new AppException("Snippet not found with ID: " + id, HttpStatus.BAD_REQUEST));
        platformRepository.deleteById(id);
    }

    @Override
    public void deleteAllSnippets() {
        platformRepository.deleteAll();
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
