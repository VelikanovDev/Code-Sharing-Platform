package com.velikanovdev.platform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record CommentResponseDto(Long id,
                                 String username,
                                 String text,
                                 @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime date) { }
