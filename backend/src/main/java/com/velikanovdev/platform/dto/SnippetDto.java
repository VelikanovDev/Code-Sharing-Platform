package com.velikanovdev.platform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

public record SnippetDto (Long id,
                          @NotEmpty String code,
                          @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime editDate,
                          @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime date,
                          UserDto user,
                          List<CommentResponseDto> comments,
                          List<RatingRequestDto> ratings) { }
