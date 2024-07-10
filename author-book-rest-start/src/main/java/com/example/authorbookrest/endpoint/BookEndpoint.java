package com.example.authorbookrest.endpoint;


import com.example.authorbookrest.dto.BookDto;
import com.example.authorbookrest.dto.BookFilterDto;
import com.example.authorbookrest.dto.BookResponseDto;
import com.example.authorbookrest.dto.SaveBookDto;
import com.example.authorbookrest.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/books")
@CrossOrigin(origins = "http://localhost:4200")
public class BookEndpoint {

    private final BookService bookService;

    @PostMapping
    public BookDto create(@Valid @RequestBody SaveBookDto saveBookDto) {
        return bookService.save(saveBookDto);
    }

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.getAll();
    }

    @PostMapping("/filter")
    public List<BookDto> getAllByFilter(@RequestBody BookFilterDto bookFilterDto) {
        return bookService.getAllByFilter(bookFilterDto);
    }

    @GetMapping("/search")
    public Page<BookResponseDto> searchByTitle(@RequestParam("title") String title,
                                               @RequestParam("page") int page,
                                               @RequestParam("size") int size) {
        return bookService.getByTitle(title, page, size);
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable int id) {
        return bookService.getById(id);
    }
}
