package com.example.authorbookrest.service;


import com.example.authorbookrest.dto.AuthorResponseDto;
import com.example.authorbookrest.dto.PagingResponseDto;
import com.example.authorbookrest.dto.SaveAuthorDto;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    AuthorResponseDto create(SaveAuthorDto author);

    PagingResponseDto getAll(Pageable pageable);

    AuthorResponseDto getById(int id);

    AuthorResponseDto update(int id, SaveAuthorDto author);

    void deleteById(int id);
}
