package com.velikanovdev.platform.dto;

import java.time.LocalDateTime;

public record RatingInfoDto(
        int value,
        String username,
        LocalDateTime date
) {
}
