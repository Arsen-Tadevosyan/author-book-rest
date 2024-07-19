package com.example.authorbookrest.service.impl;

import com.example.authorbookrest.dto.CreateUserRequestDto;
import com.example.authorbookrest.dto.UserDto;
import com.example.authorbookrest.entity.User;
import com.example.authorbookrest.exception.UserNotFoundException;
import com.example.authorbookrest.mapper.UserMapper;
import com.example.authorbookrest.repository.UserRepository;
import com.example.authorbookrest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${upload.image.path}")
    private String uploadImagePath;

    @Override
    public UserDto create(CreateUserRequestDto createUserRequest) {
        log.info("Creating user with email: {}", createUserRequest.getEmail());
        User user = userMapper.map(createUserRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        log.info("User created successfully with email: {}", savedUser.getEmail());
        return userMapper.map(savedUser);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new UserNotFoundException("User with email: " + email + " not found"));
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(()
                -> new UserNotFoundException("User with ID: " + id + " not found"));
    }

    @Override
    @SneakyThrows
    public void uploadImage(User user, MultipartFile multipartFile) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File destinationFile = new File(uploadImagePath, fileName);
            multipartFile.transferTo(destinationFile);
            user.setImagePath(fileName);
            userRepository.save(user);
            log.info("Image uploaded successfully for user email: {}", user.getName());
        } else {
            log.warn("No image file provided for user email: {}", user.getName());
        }
    }

}
