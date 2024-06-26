package com.velikanovdev.platform.service;

import com.velikanovdev.platform.dto.UserAuthDetails;
import com.velikanovdev.platform.dto.UserCredentials;
import com.velikanovdev.platform.dto.UserDto;
import com.velikanovdev.platform.dto.UserInfoDto;

import java.util.List;

public interface UserService {
    UserAuthDetails login(UserCredentials userCredentials);
    UserDto register(UserCredentials userCredentials);
    List<UserInfoDto> getAllUsers();
}
