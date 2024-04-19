package com.velikanovdev.platform.service;

import com.velikanovdev.platform.dto.UserAuthDetails;
import com.velikanovdev.platform.dto.UserCredentials;
import com.velikanovdev.platform.dto.UserDto;
import com.velikanovdev.platform.entity.User;

import java.util.List;

public interface UserService {
    UserAuthDetails login(UserCredentials userCredentials);
    UserAuthDetails register(UserCredentials userCredentials);
    List<UserDto> getAllUsers();
    User findByUsername(String username);
}
