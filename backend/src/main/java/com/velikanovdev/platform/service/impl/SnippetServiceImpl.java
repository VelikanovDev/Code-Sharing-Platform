package com.velikanovdev.platform.service.impl;

import com.velikanovdev.platform.dto.SnippetDto;
import com.velikanovdev.platform.entity.Snippet;
import com.velikanovdev.platform.entity.User;
import com.velikanovdev.platform.exception.AppException;
import com.velikanovdev.platform.mappers.SnippetMapper;
import com.velikanovdev.platform.repository.SnippetRepository;
import com.velikanovdev.platform.repository.UserRepository;
import com.velikanovdev.platform.service.SnippetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class SnippetServiceImpl implements SnippetService {
    private final SnippetRepository snippetRepository;
    private final UserRepository userRepository;

    @Autowired
    public SnippetServiceImpl(SnippetRepository snippetRepository, UserRepository userRepository) {
        this.snippetRepository = snippetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SnippetDto addSnippet(String username, SnippetDto snippetDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("User not found with username: " + username, HttpStatus.BAD_REQUEST));

        Snippet snippet = new Snippet();
        snippet.setCode(snippetDto.code());
        snippet.setDate(LocalDateTime.now());
        snippet.setUser(user);
        return SnippetMapper.INSTANCE.toSnippetDto(snippetRepository.save(snippet));
    }

    @Override
    public SnippetDto updateSnippet(SnippetDto snippet) {
        log.info("SnippetServiceImpl: updating a snippet");
        Snippet existingSnippet = snippetRepository.findById(snippet.id())
                .orElseThrow(() -> new AppException("Snippet not found with ID: " + snippet.id(), HttpStatus.BAD_REQUEST));
        existingSnippet.setEditDate(LocalDateTime.now());
        existingSnippet.setCode(snippet.code());
        return SnippetMapper.INSTANCE.toSnippetDto(snippetRepository.save(existingSnippet));
    }

    @Override
    public List<SnippetDto> getLatest() {
        return snippetRepository.findAllByOrderByDateDesc()
                .stream()
                .map(SnippetMapper.INSTANCE::toSnippetDto)
                .toList();
    }

    @Override
    public void deleteSnippet(Long id) {
        snippetRepository.findById(id)
                .orElseThrow(() -> new AppException("Snippet not found with ID: " + id, HttpStatus.BAD_REQUEST));
        snippetRepository.deleteById(id);
    }

    @Override
    public void deleteAllSnippets() {
        snippetRepository.deleteAll();
    }
}
