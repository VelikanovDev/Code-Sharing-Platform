package com.velikanovdev.platform.controller;

import com.velikanovdev.platform.dto.RatingRequestDto;
import com.velikanovdev.platform.dto.RatingResponseDto;
import com.velikanovdev.platform.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/add")
    public ResponseEntity<RatingResponseDto> addRating(@RequestBody RatingRequestDto ratingResponseDto) {
        RatingResponseDto rating = ratingService.addRating(ratingResponseDto);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/snippet/{snippetId}")
    public ResponseEntity<List<RatingResponseDto>> getRatingsBySnippet(@PathVariable Long snippetId) {
        List<RatingResponseDto> ratings = ratingService.findRatingsBySnippet(snippetId);
        return ResponseEntity.ok(ratings);
    }
}
