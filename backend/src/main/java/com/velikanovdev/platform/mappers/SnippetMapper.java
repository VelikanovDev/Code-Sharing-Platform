package com.velikanovdev.platform.mappers;

import com.velikanovdev.platform.dto.SnippetDto;
import com.velikanovdev.platform.entity.Snippet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, RatingMapper.class})
public interface SnippetMapper {
   SnippetMapper INSTANCE = Mappers.getMapper(SnippetMapper.class);

   SnippetDto toSnippetDto(Snippet snippet);
}
