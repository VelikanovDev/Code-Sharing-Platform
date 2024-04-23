package com.velikanovdev.platform.service;

import com.velikanovdev.platform.dto.SnippetDto;
import com.velikanovdev.platform.entity.Snippet;

import java.util.List;

public interface SnippetService {
    Snippet getSnippet(Long id);
    Snippet addSnippet(String username, SnippetDto snippetDto);
    Snippet updateSnippet(SnippetDto snippet);
    List<SnippetDto> getLatest();
    void deleteSnippet(Long id);
    void deleteAllSnippets();
}
