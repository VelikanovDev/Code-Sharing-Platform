package com.velikanovdev.platform.dto;

import com.velikanovdev.platform.entity.Snippet;

import java.util.List;

public record UserDto(Long id, String username, String role, List<Snippet> snippets){
}
