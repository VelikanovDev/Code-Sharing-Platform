package com.velikanovdev.platform.controller;

import com.velikanovdev.platform.config.UserAuthenticationProvider;
import com.velikanovdev.platform.dto.UserAuthDetails;
import com.velikanovdev.platform.dto.UserCredentials;
import com.velikanovdev.platform.dto.UserDto;
import com.velikanovdev.platform.dto.UserInfoDto;
import com.velikanovdev.platform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<UserAuthDetails> login(@RequestBody @Valid UserCredentials userCredentials) {
        UserAuthDetails loggedInUser = userService.login(userCredentials);
        loggedInUser.setToken(userAuthenticationProvider.createToken(loggedInUser));
        return ResponseEntity.ok(loggedInUser);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid UserCredentials userCredentials) {
        UserDto createdUser = userService.register(userCredentials);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public List<UserInfoDto> getAllUsers() {
        return userService.getAllUsers();
    }
}
