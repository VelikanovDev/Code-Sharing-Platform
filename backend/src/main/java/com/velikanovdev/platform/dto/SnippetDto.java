package com.velikanovdev.platform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.velikanovdev.platform.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record SnippetDto (Long id,
                          String code,
                          @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime editDate,
                          @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime date,
                          UserDto user,
                          List<Comment> comments) { }
