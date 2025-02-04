package com.example.authorbookrest.service.impl;

import com.example.authorbookrest.dto.AuthorResponseDto;
import com.example.authorbookrest.dto.PagingResponseDto;
import com.example.authorbookrest.dto.SaveAuthorDto;
import com.example.authorbookrest.entity.Author;
import com.example.authorbookrest.exception.AuthorNotFoundException;
import com.example.authorbookrest.mapper.AuthorMapper;
import com.example.authorbookrest.repository.AuthorRepository;
import com.example.authorbookrest.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorResponseDto create(SaveAuthorDto saveAuthorDto) {
        var savedAuthor = authorRepository.save(authorMapper.map(saveAuthorDto));
        log.info("Author created successfully with ID: {}", savedAuthor.getId());
        return authorMapper.map(savedAuthor);
    }

    @Override
    public PagingResponseDto getAll(Pageable pageable) {
        var all = authorRepository.findAll(pageable);
        var authorResponseDtos = new ArrayList<>();
        for (Author author : all.getContent()) {
            authorResponseDtos.add(authorMapper.map(author));
        }
        log.info("Fetched {} authors", authorResponseDtos.size());
        return PagingResponseDto.builder()
                .data(authorResponseDtos)
                .totalElements(all.getTotalElements())
                .size(all.getSize())
                .page(pageable.getPageNumber())
                .build();
    }

    @Override
    public AuthorResponseDto getById(int id) {
        log.info("Fetching author with ID: {}", id);
        var byId = authorRepository.findById(id).
                orElseThrow(() -> new AuthorNotFoundException("Author Not Found"));
        return authorMapper.map(byId);
    }

    @Override
    public AuthorResponseDto update(int id, SaveAuthorDto saveAuthorDto) {
        log.info("Updating author with ID: {} with new details: {}", id, saveAuthorDto);
        var updatedAuthor = authorMapper.map(saveAuthorDto);
        updatedAuthor.setId(id);
        updatedAuthor = authorRepository.save(updatedAuthor);
        log.info("Author with ID {} updated successfully", id);
        return authorMapper.map(updatedAuthor);
    }

    @Override
    public void deleteById(int id) {
        authorRepository.deleteById(id);
        log.info("Author with ID {} deleted successfully", id);
    }
}
