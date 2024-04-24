package com.velikanovdev.platform.initializer;

import com.velikanovdev.platform.dto.UserCredentials;
import com.velikanovdev.platform.repository.UserRepository;
import com.velikanovdev.platform.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public DataInitializer(UserRepository userRepository, UserServiceImpl userServiceImpl) {
        this.userRepository = userRepository;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public void run(ApplicationArguments args) {

        if(userRepository.findAll().isEmpty()) {
            userServiceImpl.register(new UserCredentials("admin", "admin"));
        }
    }
}

