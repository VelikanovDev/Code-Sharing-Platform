package com.velikanovdev.platform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record RatingResponseDto(
        @NotEmpty int ratingValue,
        String username,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime date) {}
