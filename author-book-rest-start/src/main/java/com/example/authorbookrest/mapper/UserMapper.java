package com.example.authorbookrest.mapper;

import com.example.authorbookrest.dto.CreateUserRequestDto;
import com.example.authorbookrest.dto.UserDto;
import com.example.authorbookrest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userType", constant = "USER")
    UserDto map(User user);

    User map(CreateUserRequestDto createUserRequest);

}
