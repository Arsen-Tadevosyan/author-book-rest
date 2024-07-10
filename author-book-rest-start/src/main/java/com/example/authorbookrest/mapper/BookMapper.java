package com.example.authorbookrest.mapper;

import com.example.authorbookrest.dto.BookDto;
import com.example.authorbookrest.dto.BookResponseDto;
import com.example.authorbookrest.dto.SaveBookDto;
import com.example.authorbookrest.entity.Book;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto map(Book book);

    Book map(SaveBookDto saveBookDto);

    List<BookDto> map(List<Book> books);

    default Page<BookResponseDto> map(Page<Book> books, Pageable pageable) {
        List<BookResponseDto> bookResponseDtoList = books.stream()
                .map(this::mapToBookResponseDto)
                .collect(Collectors.toList());
        return new PageImpl<>(bookResponseDtoList, pageable, books.getTotalElements());
    }

    BookResponseDto mapToBookResponseDto(Book book);
}
