package com.velikanovdev.platform.dto;

import java.util.List;

public record UserInfoDto(Long id,
                      String username,
                      String role,
                      List<SnippetCodeDto> snippets,
                      List<CommentResponseDto> comments,
                      List<RatingResponseDto> ratings){}
