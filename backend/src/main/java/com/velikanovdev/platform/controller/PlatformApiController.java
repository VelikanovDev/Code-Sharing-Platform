package com.velikanovdev.platform.controller;

import com.velikanovdev.platform.dto.SnippetDto;
import com.velikanovdev.platform.dto.UserDto;
import com.velikanovdev.platform.entity.Snippet;
import com.velikanovdev.platform.entity.User;
import com.velikanovdev.platform.service.PlatformService;
import com.velikanovdev.platform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/code")
public class PlatformApiController {
    private final PlatformService platformService;
    private final UserService userService;

    @Autowired
    public PlatformApiController(PlatformService platformService, UserService userService) {
        this.platformService = platformService;
        this.userService = userService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> addCode(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String code = payload.get("code");

        User user = userService.findByUsername(username);
        log.info("Username: " + user.getUsername());

        Snippet snippet = new Snippet();
        snippet.setCode(code);
        snippet.setDate(LocalDateTime.now());
        snippet.setUser(user);

        String id = String.valueOf(platformService.addOrUpdateSnippet(snippet));

        return ResponseEntity.ok(Map.of("id", id));
    }

    @GetMapping("/latest")
    public ResponseEntity<List<SnippetDto>> getLatest() {
        List<Snippet> snippets = platformService.getLatest();
        List<SnippetDto> dtos = snippets.stream().map(this::convertToDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping(path = "/edit/{id}", produces = "application/json")
    public ResponseEntity<Snippet> editSnippet(@PathVariable Long id, @RequestBody SnippetDto snippet) {
        log.info("Editing snippet with id " + id);
        Snippet s = platformService.getSnippet(id);

        if(s == null) {
            return ResponseEntity.notFound().build();
        }

        s.setDate(LocalDateTime.now());
        s.setCode(snippet.getCode());

        platformService.addOrUpdateSnippet(s);

        return ResponseEntity.ok().body(s);
    }

    @DeleteMapping(path = "/delete", produces = "application/json")
    public ResponseEntity<String> deleteAllSnippets() {
        log.info("Deleting all snippets");
        platformService.deleteAllSnippets();
        return ResponseEntity.ok("Snippets have been deleted");
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json")
    public ResponseEntity<String> deleteSnippet(@PathVariable Long id) {
        log.info("Deleting snippet with id " + id);
        platformService.deleteSnippet(id);
        return ResponseEntity.ok("Snippet has been deleted");
    }

    private SnippetDto convertToDTO(Snippet snippet) {
        UserDto userDTO = new UserDto(snippet.getUser().getId(), snippet.getUser().getUsername(), snippet.getUser().getRole());
        return new SnippetDto(snippet.getId(), snippet.getCode(), snippet.getDate(), userDTO);
    }
}
