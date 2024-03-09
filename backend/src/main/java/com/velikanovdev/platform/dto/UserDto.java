package com.velikanovdev.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String token;

    public UserDto(Long id, String username) {
        this.id = id;
        this.username = username;
        this.token = null;
    }
}
