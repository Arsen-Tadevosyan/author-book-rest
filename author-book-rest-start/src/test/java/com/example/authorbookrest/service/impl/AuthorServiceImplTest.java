package com.example.authorbookrest.service.impl;

import com.example.authorbookrest.dto.AuthorResponseDto;
import com.example.authorbookrest.dto.Gender;
import com.example.authorbookrest.dto.PagingResponseDto;
import com.example.authorbookrest.dto.SaveAuthorDto;
import com.example.authorbookrest.entity.Author;
import com.example.authorbookrest.repository.AuthorRepository;
import com.example.authorbookrest.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Test
    void create() {
        SaveAuthorDto saveAuthorDto = SaveAuthorDto.builder()
                .name("aaa")
                .surname("aaa")
                .age(345)
                .gender(Gender.MALE)
                .build();

        Author authorEntity = Author.builder()
                .name("aaa")
                .surname("aaa")
                .age(345)
                .gender(Gender.MALE)
                .id(1)
                .build();

        when(authorRepository.save(any())).thenReturn(authorEntity);
        when(authorRepository.findById(authorEntity.getId())).thenReturn(Optional.of(authorEntity));


        AuthorResponseDto authorResponseDto = authorService.create(saveAuthorDto);
        AuthorResponseDto byId = authorService.getById(authorResponseDto.getId());

        assertEquals(saveAuthorDto.getName(), byId.getFirstName());
        assertEquals(saveAuthorDto.getSurname(), byId.getSurname());
        assertEquals(saveAuthorDto.getAge(), byId.getAge());
        assertEquals(saveAuthorDto.getGender(), byId.getGender());

        verify(authorRepository, times(1)).save(any());
        verify(authorRepository, times(1)).findById(1);
    }

    @Test
    void getAll() {
        Author authorEntity = Author.builder()
                .name("aaa")
                .surname("aaa")
                .age(345)
                .gender(Gender.MALE)
                .id(1)
                .build();
        Author authorEntity2 = Author.builder()
                .name("xxx")
                .surname("xxx")
                .age(353)
                .gender(Gender.MALE)
                .id(2)
                .build();
        PageRequest pageRequest = PageRequest.of(0, 1);
        PageImpl<Author> page = new PageImpl<>(List.of(authorEntity, authorEntity2), pageRequest, 50);
        when(authorRepository.findAll(any(Pageable.class))).thenReturn(page);

        PagingResponseDto all = authorService.getAll(pageRequest);
        assertEquals(2, page.getContent().size());

    }
}