package com.velikanovdev.platform.initializer;

import com.velikanovdev.platform.dto.UserCredentials;
import com.velikanovdev.platform.entity.Role;
import com.velikanovdev.platform.repository.RoleRepository;
import com.velikanovdev.platform.repository.UserRepository;
import com.velikanovdev.platform.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, UserServiceImpl userServiceImpl) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            roleRepository.save(new Role("ADMIN"));
        }

        if (roleRepository.findByName("USER").isEmpty()) {
            roleRepository.save(new Role("USER"));
        }

        if(userRepository.findAll().isEmpty()) {
            userServiceImpl.register(new UserCredentials("admin", "admin"));
        }
    }
}

