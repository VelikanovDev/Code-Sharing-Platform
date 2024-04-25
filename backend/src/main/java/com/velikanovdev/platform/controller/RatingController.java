package com.velikanovdev.platform.controller;

import com.velikanovdev.platform.dto.RatingDto;
import com.velikanovdev.platform.dto.RatingInfoDto;
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
    public ResponseEntity<RatingInfoDto> addRating(@RequestBody RatingDto ratingDto) {
        System.out.println(ratingDto.toString());
        RatingInfoDto rating = ratingService.addRating(ratingDto);
        System.out.println("End of the addRating controller");
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/snippet/{snippetId}")
    public ResponseEntity<List<RatingInfoDto>> getRatingsBySnippet(@PathVariable Long snippetId) {
        List<RatingInfoDto> ratings = ratingService.findRatingsBySnippet(snippetId);
        return ResponseEntity.ok(ratings);
    }
}
