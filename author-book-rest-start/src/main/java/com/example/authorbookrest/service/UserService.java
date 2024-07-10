package com.example.authorbookrest.service;


import com.example.authorbookrest.dto.CreateUserRequestDto;
import com.example.authorbookrest.dto.UserDto;
import com.example.authorbookrest.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    UserDto create(CreateUserRequestDto createUserRequest);

    User findByEmail(String email);

    User findById(int id);

    void uploadImage(User byId, MultipartFile multipartFile) throws IOException;
}
