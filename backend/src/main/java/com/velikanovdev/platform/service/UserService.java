package com.velikanovdev.platform.service;

import com.velikanovdev.platform.dto.CredentialsDto;
import com.velikanovdev.platform.dto.SignUpDto;
import com.velikanovdev.platform.dto.UserDto;
import com.velikanovdev.platform.entity.User;
import com.velikanovdev.platform.enums.Role;
import com.velikanovdev.platform.exception.AppException;
import com.velikanovdev.platform.mappers.UserMapper;
import com.velikanovdev.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByUsername(credentialsDto.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userDto.username());

        if (optionalUser.isPresent()) {
            throw new AppException("User with login " + userDto.username() + " already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);

        if(userRepository.findAll().isEmpty()) {
            user.setRole(Role.ADMIN);
        }
        else {
            user.setRole(Role.USER);
        }

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
    }

}
