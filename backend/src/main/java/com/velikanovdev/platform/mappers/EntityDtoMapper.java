package com.velikanovdev.platform.mappers;

import com.velikanovdev.platform.dto.*;
import com.velikanovdev.platform.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntityDtoMapper {
    EntityDtoMapper INSTANCE = Mappers.getMapper(EntityDtoMapper.class);

    UserDto toUserDto(User user);
    UserAuthDetails toUserAuthDetails(User user);
    SnippetDto toSnippetDto(Snippet snippet);
    SnippetCodeDto toSnippetCodeDto(Snippet snippet);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(UserCredentials registerRequest);

    Rating toRating(RatingRequestDto ratingDto);

    @Mappings({
            @Mapping(target = "username", source = "rating.user.username")
    })
    RatingResponseDto toRatingResponseDto(Rating rating);

    CommentResponseDto toCommentResponseDto(Comment comment);
}