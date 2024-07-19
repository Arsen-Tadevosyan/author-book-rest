package com.example.authorbookrest.mapper;

import com.example.authorbookrest.dto.AuthorResponseDto;
import com.example.authorbookrest.dto.SaveAuthorDto;
import com.example.authorbookrest.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = LocalDate.class)
public interface AuthorMapper {

    @Mapping(target = "firstName", source = "name")
    AuthorResponseDto map(Author author);

    @Mapping(target = "createdDate", expression = "java(LocalDate.now())")
    Author map(SaveAuthorDto saveAuthorDto);

}
