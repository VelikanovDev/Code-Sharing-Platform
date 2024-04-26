package com.velikanovdev.platform.dto;

import java.util.List;

public record UserDto(Long id, String username, String role, List<SnippetCodeDto> snippetCodeDto){
}
