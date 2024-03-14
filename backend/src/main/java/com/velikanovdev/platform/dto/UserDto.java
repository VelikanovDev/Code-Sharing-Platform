package com.velikanovdev.platform.dto;

import com.velikanovdev.platform.enums.Role;
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
    private Role role;
    private String token;

    public UserDto(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.token = null;
    }
}
