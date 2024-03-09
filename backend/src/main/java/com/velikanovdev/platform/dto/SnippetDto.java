package com.velikanovdev.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SnippetDto {
    private Long id;
    private String code;
    private String date;
    private UserDto user;
}
