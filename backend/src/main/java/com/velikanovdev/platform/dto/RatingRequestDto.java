package com.velikanovdev.platform.dto;

import jakarta.validation.constraints.NotEmpty;

public record RatingRequestDto(
        @NotEmpty int value,
        String username,
        Long snippetId) {}
