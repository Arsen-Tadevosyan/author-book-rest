package com.example.authorbookrest.service.impl;

import com.example.authorbookrest.dto.BookDto;
import com.example.authorbookrest.entity.Book;
import com.example.authorbookrest.entity.FavoriteBook;
import com.example.authorbookrest.entity.User;
import com.example.authorbookrest.exception.FavoriteBookAlreadyExistException;
import com.example.authorbookrest.exception.FavoriteBookDoseNotExistException;
import com.example.authorbookrest.mapper.BookMapper;
import com.example.authorbookrest.repository.FavoriteBookRepository;
import com.example.authorbookrest.service.BookService;
import com.example.authorbookrest.service.FavoriteBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteBookImpl implements FavoriteBookService {

    private final FavoriteBookRepository favoriteBookRepository;
    private final BookService bookService;
    private final BookMapper bookMapper;

    @Override
    public FavoriteBook save(int bookId, User user) {
        var book = bookService.findById(bookId);
        favoriteBookRepository.findByUserAndBook(user, book).ifPresent(existing -> {
            throw new FavoriteBookAlreadyExistException("Favorite Book already exists with ID: " + bookId);
        });
        var savedFavoriteBook = favoriteBookRepository.save(FavoriteBook.builder()
                .book(book)
                .user(user)
                .build());
        log.info("Favorite book saved successfully with ID: {}", savedFavoriteBook.getId());
        return savedFavoriteBook;
    }

    @Override
    public List<BookDto> findByUser(User user) {
        var byUser = favoriteBookRepository.findByUser(user);
        List<Book> books = new ArrayList<>();
        for (FavoriteBook favoriteBook : byUser) {
            books.add(favoriteBook.getBook());
        }
        log.info("Fetched  favorite books for user: {}", user.getName());
        return bookMapper.mapToDto(books);
    }

    @Override
    public void delete(User user, int id) {
        var book = bookService.findById(id);
        var byUserAndBook = favoriteBookRepository.findByUserAndBook(user, book).orElseThrow(()
                -> new FavoriteBookDoseNotExistException("Book dose not exist with ID: " + id));
        favoriteBookRepository.delete(byUserAndBook);
        log.info("Favorite book with ID: {} deleted for user: {}", id, user.getName());
    }
}
