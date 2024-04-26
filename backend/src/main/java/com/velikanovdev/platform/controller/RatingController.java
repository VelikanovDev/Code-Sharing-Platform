package com.velikanovdev.platform.controller;

import com.velikanovdev.platform.dto.RatingRequestDto;
import com.velikanovdev.platform.dto.RatingResponseDto;
import com.velikanovdev.platform.service.RatingService;
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
        System.out.println(ratingResponseDto.toString());
        RatingResponseDto rating = ratingService.addRating(ratingResponseDto);
        System.out.println("End of the addRating controller");
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/snippet/{snippetId}")
    public ResponseEntity<List<RatingResponseDto>> getRatingsBySnippet(@PathVariable Long snippetId) {
        List<RatingResponseDto> ratings = ratingService.findRatingsBySnippet(snippetId);
        return ResponseEntity.ok(ratings);
    }
}
