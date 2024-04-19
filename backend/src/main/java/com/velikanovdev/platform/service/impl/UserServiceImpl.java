package com.velikanovdev.platform.service.impl;

import com.velikanovdev.platform.dto.UserAuthDetails;
import com.velikanovdev.platform.dto.UserCredentials;
import com.velikanovdev.platform.dto.UserDto;
import com.velikanovdev.platform.entity.User;
import com.velikanovdev.platform.exception.AppException;
import com.velikanovdev.platform.mappers.UserMapper;
import com.velikanovdev.platform.repository.RoleRepository;
import com.velikanovdev.platform.repository.UserRepository;
import com.velikanovdev.platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserAuthDetails login(UserCredentials userCredentials) {
        User user = userRepository.findByUsername(userCredentials.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));

        if (passwordEncoder.matches(CharBuffer.wrap(userCredentials.password()), user.getPassword())) {
            return UserMapper.INSTANCE.toUserAuthDetails(user);
        }

        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserAuthDetails register(UserCredentials userCredentials) {
        Optional<User> optionalUser = userRepository.findByUsername(userCredentials.username());

        if (optionalUser.isPresent()) {
            throw new AppException("User with login " + userCredentials.username() + " already exists", HttpStatus.CONFLICT);
        }

        User user = UserMapper.INSTANCE.signUpToUser(userCredentials);

        if(userRepository.findAll().isEmpty()) {
            user.setRole(roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new AppException("Role 'ADMIN' not found", HttpStatus.NOT_FOUND)));
        }
        else {
            user.setRole(roleRepository.findByName("USER")
                    .orElseThrow(() -> new AppException("Role 'USER' not found", HttpStatus.NOT_FOUND)));
        }

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userCredentials.password())));

        User savedUser = userRepository.save(user);

        return UserMapper.INSTANCE.toUserAuthDetails(savedUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(UserMapper.INSTANCE::toUserDto).toList();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("User with username '" + username + "' not found", HttpStatus.NOT_FOUND));
    }
}
