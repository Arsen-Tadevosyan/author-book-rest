package com.example.authorbookrest.mapper;

import com.example.authorbookrest.dto.CreateUserRequestDto;
import com.example.authorbookrest.dto.UserDto;
import com.example.authorbookrest.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto map(User user);

    User map(CreateUserRequestDto createUserRequest);

    List<UserDto> map(List<User> random);
}
