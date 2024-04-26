package com.velikanovdev.platform.dto;

public record CommentRequestDto(Long snippetId, String username, String text) {
}
