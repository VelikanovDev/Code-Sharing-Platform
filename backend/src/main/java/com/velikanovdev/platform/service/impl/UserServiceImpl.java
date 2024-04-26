package com.velikanovdev.platform.service.impl;

import com.velikanovdev.platform.dto.*;
import com.velikanovdev.platform.entity.Comment;
import com.velikanovdev.platform.entity.Rating;
import com.velikanovdev.platform.entity.User;
import com.velikanovdev.platform.enums.Role;
import com.velikanovdev.platform.exception.AppException;
import com.velikanovdev.platform.mappers.EntityDtoMapper;
import com.velikanovdev.platform.repository.SnippetRepository;
import com.velikanovdev.platform.repository.UserRepository;
import com.velikanovdev.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SnippetRepository snippetRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, SnippetRepository snippetRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.snippetRepository = snippetRepository;
    }

    @Override
    public UserAuthDetails login(UserCredentials userCredentials) {
        User user = userRepository.findByUsername(userCredentials.username())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));

        if (passwordEncoder.matches(CharBuffer.wrap(userCredentials.password()), user.getPassword())) {
            return EntityDtoMapper.INSTANCE.toUserAuthDetails(user);
        }

        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserDto register(UserCredentials userCredentials) {
        Optional<User> optionalUser = userRepository.findByUsername(userCredentials.username());

        if (optionalUser.isPresent()) {
            throw new AppException("User with login " + userCredentials.username() + " already exists", HttpStatus.CONFLICT);
        }

        User user = EntityDtoMapper.INSTANCE.signUpToUser(userCredentials);

        if(userRepository.findAll().isEmpty()) {
           user.setRole(Role.ADMIN);
        }
        else {
            user.setRole(Role.USER);
        }

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userCredentials.password())));

        User savedUser = userRepository.save(user);

        return EntityDtoMapper.INSTANCE.toUserDto(savedUser);
    }

    @Override
    public List<UserInfoDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return convertToUserInfoDto(users);
    }

    private List<UserInfoDto> convertToUserInfoDto(List<User> users) {
        List<UserInfoDto> userInfoDtos = new ArrayList<>();
        for(User u: users) {
            List<Comment> comments = snippetRepository.findAllCommentsByUsername(u.getUsername());
            List<Rating> ratings = snippetRepository.findAllRatingsByUsername(u.getUsername());

            List<SnippetCodeDto> snippetCodeDtos = u.getSnippets().stream()
                    .map(EntityDtoMapper.INSTANCE::toSnippetCodeDto).toList();

            List<CommentResponseDto> commentResponseDtos = comments.stream()
                    .map(EntityDtoMapper.INSTANCE::toCommentResponseDto).toList();

            List<RatingResponseDto> ratingResponseDtos = ratings.stream()
                    .map(EntityDtoMapper.INSTANCE::toRatingResponseDto).toList();

            UserInfoDto userInfoDto = new UserInfoDto(u.getId(), u.getUsername(), u.getRole().toString(),
                    snippetCodeDtos, commentResponseDtos, ratingResponseDtos);

            userInfoDtos.add(userInfoDto);
        }

        return userInfoDtos;
    }
}
