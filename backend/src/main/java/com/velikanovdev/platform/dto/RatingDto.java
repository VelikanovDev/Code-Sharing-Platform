package com.velikanovdev.platform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record RatingDto(
        @NotEmpty int value,
        String username,
        Long snippetId,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime date) {}
