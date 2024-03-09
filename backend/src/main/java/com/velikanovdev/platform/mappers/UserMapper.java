package com.velikanovdev.platform.mappers;

import com.velikanovdev.platform.dto.SignUpDto;
import com.velikanovdev.platform.dto.UserDto;
import com.velikanovdev.platform.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
