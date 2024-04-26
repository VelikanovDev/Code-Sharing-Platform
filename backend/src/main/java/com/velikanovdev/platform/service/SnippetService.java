package com.velikanovdev.platform.service;

import com.velikanovdev.platform.dto.SnippetCodeDto;
import com.velikanovdev.platform.dto.SnippetDto;

import java.util.List;

public interface SnippetService {
    SnippetDto addSnippet(String username, SnippetCodeDto snippetDto);
    SnippetDto updateSnippet(SnippetCodeDto snippet);
    List<SnippetDto> getLatest();
    void deleteSnippet(Long id);
    void deleteAllSnippets();
}
