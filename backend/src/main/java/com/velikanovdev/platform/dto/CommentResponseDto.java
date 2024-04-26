package com.velikanovdev.platform.dto;

import java.time.LocalDateTime;

public record CommentResponseDto(Long id, String username, String text, LocalDateTime date) {
}
