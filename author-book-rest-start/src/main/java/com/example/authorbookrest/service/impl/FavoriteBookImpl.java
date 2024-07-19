package com.example.authorbookrest.service.impl;

import com.example.authorbookrest.dto.BookDto;
import com.example.authorbookrest.entity.Book;
import com.example.authorbookrest.entity.FavoriteBook;
import com.example.authorbookrest.entity.User;
import com.example.authorbookrest.exception.BookNotFoundException;
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
    public FavoriteBook save(int id, User user) {
        Book book = bookService.findById(id);
        if (book == null) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }
        FavoriteBook favoriteBook = favoriteBookRepository.findByUserAndBook(user, book);
        if (favoriteBook != null) {
            throw new FavoriteBookAlreadyExistException("Book already exist with ID: " + id);
        }
        FavoriteBook savedFavoriteBook = favoriteBookRepository.save(FavoriteBook.builder()
                .book(book)
                .user(user)
                .build());
        log.info("Favorite book saved successfully with ID: {}", savedFavoriteBook.getId());
        return savedFavoriteBook;
    }

    @Override
    public List<BookDto> findByUser(User user) {
        List<FavoriteBook> byUser = favoriteBookRepository.findByUser(user);
        List<Book> books = new ArrayList<>();
        for (FavoriteBook favoriteBook : byUser) {
            books.add(favoriteBook.getBook());
        }
        log.info("Fetched  favorite books for user: {}", user.getName());
        return bookMapper.mapToDto(books);
    }

    @Override
    public void delete(User user, int id) {
        Book book = bookService.findById(id);
        if (book == null) {
            log.warn("Book not found with ID: {}", id);
            throw new BookNotFoundException("Book not found with ID: " + id);
        }
        FavoriteBook byUserAndBook = favoriteBookRepository.findByUserAndBook(user, book);
        if (byUserAndBook != null) {
            favoriteBookRepository.delete(byUserAndBook);
            log.info("Favorite book with ID: {} deleted for user: {}", id, user.getName());
        } else {
            throw new FavoriteBookDoseNotExistException("Book dose not exist with ID: " + id);
        }
    }
}
