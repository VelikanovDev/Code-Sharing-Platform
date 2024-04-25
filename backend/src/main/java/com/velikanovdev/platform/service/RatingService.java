package com.velikanovdev.platform.service;

import com.velikanovdev.platform.dto.RatingDto;
import com.velikanovdev.platform.dto.RatingInfoDto;
import com.velikanovdev.platform.entity.Rating;

import java.util.List;

public interface RatingService {
    RatingInfoDto addRating(RatingDto ratingDto);
    List<RatingInfoDto> findRatingsBySnippet(Long snippetId);
}
