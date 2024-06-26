package com.velikanovdev.platform.controller;

import com.velikanovdev.platform.dto.SnippetCodeDto;
import com.velikanovdev.platform.dto.SnippetDto;
import com.velikanovdev.platform.service.SnippetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/snippet")
public class SnippetController {
    private final SnippetService snippetService;

    @Autowired
    public SnippetController(SnippetService snippetService) {
        this.snippetService = snippetService;
    }

    @PostMapping(path ="/new", produces = "application/json")
    public ResponseEntity<SnippetDto> addSnippet(@AuthenticationPrincipal UserDetails userDetails,
                                        @RequestBody SnippetCodeDto snippetCodeDto) {
        String username = userDetails.getUsername();
        SnippetDto newSnippet = snippetService.addSnippet(username, snippetCodeDto);
        return new ResponseEntity<>(newSnippet, HttpStatus.CREATED);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<SnippetDto>> getLatest() {
        List<SnippetDto> snippets = snippetService.getLatest();
        return ResponseEntity.ok(snippets);
    }

    @PutMapping(path = "/update/{id}", produces = "application/json")
    public ResponseEntity<SnippetDto> updateSnippet(@PathVariable Long id, @RequestBody SnippetCodeDto snippetCodeDto) {
        log.info("SnippetController: Update snippet with id " + id);
        SnippetDto updatedSnippet = snippetService.updateSnippet(snippetCodeDto);

        if (updatedSnippet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedSnippet);
    }

    @DeleteMapping(path = "/deleteAll", produces = "application/json")
    public ResponseEntity<Void> deleteAllSnippets() {
        log.info("Deleting all snippets");
        snippetService.deleteAllSnippets();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json")
    public ResponseEntity<SnippetDto> deleteSnippet(@PathVariable Long id) {
        log.info("Deleting snippet with id " + id);
        SnippetDto snippetToDelete = snippetService.deleteSnippet(id);
        return ResponseEntity.ok(snippetToDelete);
    }
}
