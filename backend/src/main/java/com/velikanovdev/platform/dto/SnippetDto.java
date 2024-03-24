package com.velikanovdev.platform.dto;

public record SnippetDto (Long id, String code, String editDate, String date, UserDto user) { }
