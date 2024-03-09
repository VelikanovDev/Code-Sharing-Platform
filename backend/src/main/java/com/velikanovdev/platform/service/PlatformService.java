package com.velikanovdev.platform.service;

import com.velikanovdev.platform.entity.Snippet;

import java.util.List;

public interface PlatformService {
    Snippet getSnippet(Long id);
    Long addOrUpdateSnippet(Snippet snippet);
    List<Snippet> getLatest();
    void deleteSnippet(Long id);
    void deleteAllSnippets();
}
