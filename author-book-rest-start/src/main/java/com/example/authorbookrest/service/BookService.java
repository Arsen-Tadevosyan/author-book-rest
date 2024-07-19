package com.example.authorbookrest.service;

import com.example.authorbookrest.dto.BookDto;
import com.example.authorbookrest.dto.BookFilterDto;
import com.example.authorbookrest.dto.BookResponseDto;
import com.example.authorbookrest.dto.SaveBookDto;
import com.example.authorbookrest.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookDto save(SaveBookDto saveBookDto);

    List<BookDto> getRandom();

    Page<BookDto> getAllByFilter(BookFilterDto bookFilterDto,Pageable pageable);

    Page<BookResponseDto> getByTitle(String title, int page, int size);

    BookDto getById(int id);

    Book findById(int id);
}
