package com.velikanovdev.platform.mappers;

import com.velikanovdev.platform.dto.RatingDto;
import com.velikanovdev.platform.dto.RatingInfoDto;
import com.velikanovdev.platform.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RatingMapper {
    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    Rating toRating(RatingDto ratingDto);

    @Mappings({
            @Mapping(target = "username", source = "rating.user.username")
    })
    RatingInfoDto toRatingInfoDto(Rating rating);

}
