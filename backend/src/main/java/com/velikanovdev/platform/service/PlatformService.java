package com.velikanovdev.platform.service;

import com.velikanovdev.platform.dto.SnippetDto;
import com.velikanovdev.platform.entity.Comment;
import com.velikanovdev.platform.entity.Snippet;

import java.util.List;

public interface PlatformService {
    Snippet getSnippet(Long id);
    Long addOrUpdateSnippet(Snippet snippet);
    List<SnippetDto> getLatest();
    void deleteSnippet(Long id);
    void deleteAllSnippets();
    Comment addComment(Comment comment);
    void deleteComment(Long id);
}
