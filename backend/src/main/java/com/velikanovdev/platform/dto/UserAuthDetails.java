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
public class UserAuthDetails {
    private Long id;
    private String username;
    private Role role;
    private String token;
}
