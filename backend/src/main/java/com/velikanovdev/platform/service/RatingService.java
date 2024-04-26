package com.velikanovdev.platform.service;

import com.velikanovdev.platform.dto.RatingRequestDto;
import com.velikanovdev.platform.dto.RatingResponseDto;

import java.util.List;

public interface RatingService {
    RatingResponseDto addRating(RatingRequestDto ratingResponseDto);
    List<RatingResponseDto> findRatingsBySnippet(Long snippetId);
}
