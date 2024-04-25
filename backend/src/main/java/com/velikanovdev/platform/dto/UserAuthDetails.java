package com.velikanovdev.platform.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String username;
    @JsonIgnore
    private Role role;
    private String token;
}
