package com.example.authorbookrest.service;

import com.example.authorbookrest.dto.BookDto;
import com.example.authorbookrest.dto.BookFilterDto;
import com.example.authorbookrest.dto.BookResponseDto;
import com.example.authorbookrest.dto.SaveBookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookDto save(SaveBookDto saveBookDto);

    List<BookDto> getAll();

    List<BookDto> getAllByFilter(BookFilterDto bookFilterDto);

    Page<BookResponseDto> getByTitle(String title, int page, int size);

    BookDto getById(int id);
}
