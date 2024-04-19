package com.velikanovdev.platform.controller;

import com.velikanovdev.platform.config.UserAuthenticationProvider;
import com.velikanovdev.platform.dto.UserAuthDetails;
import com.velikanovdev.platform.dto.UserCredentials;
import com.velikanovdev.platform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<UserAuthDetails> login(@RequestBody @Valid UserCredentials userCredentials) {
        UserAuthDetails loggedInUser = userService.login(userCredentials);
        loggedInUser.setToken(userAuthenticationProvider.createToken(loggedInUser));
        return ResponseEntity.ok(loggedInUser);
    }

    @PostMapping("/register")
    public ResponseEntity<UserAuthDetails> register(@RequestBody @Valid UserCredentials userCredentials) {
        UserAuthDetails createdUser = userService.register(userCredentials);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }
}
