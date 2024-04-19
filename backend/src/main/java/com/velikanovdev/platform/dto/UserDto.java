package com.velikanovdev.platform.dto;

import com.velikanovdev.platform.entity.Snippet;
import com.velikanovdev.platform.enums.Role;

import java.util.List;

public record UserDto(Long id, String username, Role role, List<Snippet> snippets){
}
