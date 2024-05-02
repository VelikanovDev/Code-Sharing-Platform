package com.velikanovdev.platform.dto;

import org.hibernate.validator.constraints.Length;

public record UserCredentials(String username, @Length(min = 4, max = 8) String password) { }
