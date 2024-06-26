package com.velikanovdev.platform.service.impl;

import com.velikanovdev.platform.dto.RatingRequestDto;
import com.velikanovdev.platform.dto.RatingResponseDto;
import com.velikanovdev.platform.entity.Rating;
import com.velikanovdev.platform.entity.Snippet;
import com.velikanovdev.platform.entity.User;
import com.velikanovdev.platform.exception.AppException;
import com.velikanovdev.platform.mappers.EntityDtoMapper;
import com.velikanovdev.platform.repository.RatingRepository;
import com.velikanovdev.platform.repository.SnippetRepository;
import com.velikanovdev.platform.repository.UserRepository;
import com.velikanovdev.platform.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final SnippetRepository snippetRepository;

    public RatingServiceImpl(RatingRepository ratingRepository,
                             UserRepository userRepository,
                             SnippetRepository snippetRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.snippetRepository = snippetRepository;
    }

    @Override
    public RatingResponseDto addRating(RatingRequestDto ratingRequestDto) {
        Rating rating = EntityDtoMapper.INSTANCE.toRating(ratingRequestDto);

        User user = userRepository.findByUsername(ratingRequestDto.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));

        Snippet snippet = snippetRepository.findById(ratingRequestDto.snippetId())
                .orElseThrow(() -> new AppException("Unknown snippet", HttpStatus.BAD_REQUEST));

        Rating existedRating = ratingRepository.findByUsernameAndSnippet(user.getUsername(), snippet);

        if(existedRating != null) {
            ratingRepository.delete(existedRating);
        }

        rating.setUsername(user.getUsername());
        rating.setSnippet(snippet);
        rating.setDate(LocalDateTime.now());

        return EntityDtoMapper.INSTANCE.toRatingResponseDto(ratingRepository.save(rating));
    }

    @Override
    public List<RatingResponseDto> findRatingsBySnippet(Long snippetId) {
        Snippet snippet = snippetRepository.findById(snippetId)
                .orElseThrow(() -> new AppException("Unknown snippet", HttpStatus.BAD_REQUEST));

        return snippet.getRatings().stream().map(EntityDtoMapper.INSTANCE::toRatingResponseDto).toList();
    }
}
