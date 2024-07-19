package com.example.authorbookrest.service.impl;

import com.example.authorbookrest.dto.BookDto;
import com.example.authorbookrest.dto.BookFilterDto;
import com.example.authorbookrest.dto.BookResponseDto;
import com.example.authorbookrest.dto.SaveBookDto;
import com.example.authorbookrest.entity.Book;
import com.example.authorbookrest.entity.QBook;
import com.example.authorbookrest.exception.AuthorNotFoundException;
import com.example.authorbookrest.exception.BookNotFoundException;
import com.example.authorbookrest.mapper.BookMapper;
import com.example.authorbookrest.repository.AuthorRepository;
import com.example.authorbookrest.repository.BookRepository;
import com.example.authorbookrest.service.BookService;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;
    private final EntityManager entityManager;

    @Override
    public BookDto save(SaveBookDto saveBookDto) {
        var book = bookMapper.map(saveBookDto);
        book.setAuthor(authorRepository.findById(saveBookDto.getAuthorId()).orElseThrow(() ->
                new AuthorNotFoundException("Author does not exist with Id: " + saveBookDto.getAuthorId())));
        var savedUser = bookRepository.save(book);
        log.info("Book saved successfully with ID: {}", savedUser.getId());
        return bookMapper.map(savedUser);
    }

    @Override
    public List<BookDto> getRandom() {
        var random = bookRepository.findRandomLimited();
        var bookDtos = bookMapper.mapToDto(random);
        log.info("Fetched {} random books", bookDtos.size());
        return bookDtos;
    }

    @Override
    public Page<BookDto> getAllByFilter(BookFilterDto bookFilterDto, Pageable pageable) {
        JPAQuery<Book> query = new JPAQuery<>(entityManager);
        QBook qBook = QBook.book;
        JPAQuery<Book> from = query.from(qBook);

        if (StringUtils.isNotBlank(bookFilterDto.getTitle())) {
            from.where(qBook.title.containsIgnoreCase(bookFilterDto.getTitle()));
        }
        if (StringUtils.isNotBlank(bookFilterDto.getDescription())) {
            from.where(qBook.description.containsIgnoreCase(bookFilterDto.getDescription()));
        }
        if (bookFilterDto.getMinPrice() != null) {
            from.where(qBook.price.goe(bookFilterDto.getMinPrice()));
        }
        if (bookFilterDto.getMaxPrice() != null) {
            from.where(qBook.price.loe(bookFilterDto.getMaxPrice()));
        }

        from.offset(pageable.getOffset());
        from.limit(pageable.getPageSize());

        List<Book> books = from.fetch();
        long total = from.fetchCount();

        log.info("Fetched {} books by filter", books.size());
        List<BookDto> bookDtos = bookMapper.map(books);
        return new PageImpl<>(bookDtos, pageable, total);
    }

    @Override
    public Page<BookResponseDto> getByTitle(String title, int page, int size) {
        var pageable = PageRequest.of(page, size);
        var byTitle = bookRepository.findByTitleContaining(title, pageable);
        log.info("Fetched {} books by title", byTitle.getTotalElements());
        return bookMapper.map(byTitle, byTitle.getPageable());
    }

    @Override
    public BookDto getById(int id) {
        var byId = bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Book not found with ID: " + id));
        log.info("Book found with ID: {}", id);
        return bookMapper.map(byId);
    }

    @Override
    public Book findById(int id) {
        log.info("Fetching book entity by ID: {}", id);
        return bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Book not found with ID: " + id));
    }
}
