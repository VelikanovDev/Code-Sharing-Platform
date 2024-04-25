package com.velikanovdev.platform.service.impl;

import com.velikanovdev.platform.dto.RatingDto;
import com.velikanovdev.platform.dto.RatingInfoDto;
import com.velikanovdev.platform.entity.Rating;
import com.velikanovdev.platform.entity.Snippet;
import com.velikanovdev.platform.entity.User;
import com.velikanovdev.platform.exception.AppException;
import com.velikanovdev.platform.mappers.RatingMapper;
import com.velikanovdev.platform.repository.RatingRepository;
import com.velikanovdev.platform.repository.SnippetRepository;
import com.velikanovdev.platform.repository.UserRepository;
import com.velikanovdev.platform.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public RatingInfoDto addRating(RatingDto ratingDto) {
        System.out.println("Service: " + ratingDto.toString());
        Rating rating = RatingMapper.INSTANCE.toRating(ratingDto);

        User user = userRepository.findByUsername(ratingDto.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));

        Snippet snippet = snippetRepository.findById(ratingDto.snippetId())
                .orElseThrow(() -> new AppException("Unknown snippet", HttpStatus.BAD_REQUEST));

        System.out.println("Service: " + rating.toString());
        rating.setUser(user);
        rating.setSnippet(snippet);
        rating.setDate(LocalDateTime.now());

        return RatingMapper.INSTANCE.toRatingInfoDto(ratingRepository.save(rating));
    }

    @Override
    public List<RatingInfoDto> findRatingsBySnippet(Long snippetId) {
        Snippet snippet = snippetRepository.findById(snippetId)
                .orElseThrow(() -> new AppException("Unknown snippet", HttpStatus.BAD_REQUEST));

        return snippet.getRatings().stream().map(RatingMapper.INSTANCE::toRatingInfoDto).toList();
    }
}
