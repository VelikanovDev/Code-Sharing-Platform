package com.velikanovdev.platform.service;

import com.velikanovdev.platform.dto.SnippetDto;

import java.util.List;

public interface SnippetService {
    SnippetDto addSnippet(String username, SnippetDto snippetDto);
    SnippetDto updateSnippet(SnippetDto snippet);
    List<SnippetDto> getLatest();
    void deleteSnippet(Long id);
    void deleteAllSnippets();
}
