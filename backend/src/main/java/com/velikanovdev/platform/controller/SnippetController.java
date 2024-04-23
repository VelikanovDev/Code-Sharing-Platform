package com.velikanovdev.platform.controller;

import com.velikanovdev.platform.dto.SnippetDto;
import com.velikanovdev.platform.entity.Snippet;
import com.velikanovdev.platform.service.SnippetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/snippet")
public class SnippetController {
    private final SnippetService snippetService;

    @Autowired
    public SnippetController(SnippetService snippetService) {
        this.snippetService = snippetService;
    }

    @PostMapping(path ="/new", produces = "application/json")
    public ResponseEntity<Snippet> addSnippet(@AuthenticationPrincipal UserDetails userDetails,
                                        @RequestBody SnippetDto snippetDto) {
        String username = userDetails.getUsername();
        Snippet newSnippet = snippetService.addSnippet(username, snippetDto);
        return ResponseEntity.ok(newSnippet);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<SnippetDto>> getLatest() {
        List<SnippetDto> snippets = snippetService.getLatest();
        return ResponseEntity.ok(snippets);
    }

    @PostMapping(path = "/update/{id}", produces = "application/json")
    public ResponseEntity<Snippet> updateSnippet(@PathVariable Long id, @RequestBody SnippetDto snippet) {
        log.info("SnippetController: Update snippet with id " + id);
        Snippet updatedSnippet = snippetService.updateSnippet(snippet);
        return ResponseEntity.ok(updatedSnippet);
    }

    @DeleteMapping(path = "/delete", produces = "application/json")
    public ResponseEntity<String> deleteAllSnippets() {
        log.info("Deleting all snippets");
        snippetService.deleteAllSnippets();
        return ResponseEntity.ok("Snippets have been deleted");
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json")
    public ResponseEntity<String> deleteSnippet(@PathVariable Long id) {
        log.info("Deleting snippet with id " + id);
        snippetService.deleteSnippet(id);
        return ResponseEntity.ok("Snippet has been deleted");
    }
}
