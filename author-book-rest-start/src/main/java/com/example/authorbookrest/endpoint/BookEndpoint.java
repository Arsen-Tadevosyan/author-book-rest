package com.example.authorbookrest.endpoint;

import com.example.authorbookrest.dto.BookDto;
import com.example.authorbookrest.dto.BookFilterDto;
import com.example.authorbookrest.dto.BookResponseDto;
import com.example.authorbookrest.dto.SaveBookDto;
import com.example.authorbookrest.entity.Comment;
import com.example.authorbookrest.entity.FavoriteBook;
import com.example.authorbookrest.security.CurrentUser;
import com.example.authorbookrest.service.BookService;
import com.example.authorbookrest.service.CommentService;
import com.example.authorbookrest.service.FavoriteBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/books")
@CrossOrigin(origins = "http://localhost:4200")
public class BookEndpoint {

    private final BookService bookService;
    private final FavoriteBookService favoriteBookService;
    private final CommentService commentService;

    @PostMapping
    public BookDto create(@Valid @RequestBody SaveBookDto saveBookDto) {
        return bookService.save(saveBookDto);
    }

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.getRandom();
    }

    @PostMapping("/filter")
    public Page<BookDto> getAllByFilter(@RequestBody BookFilterDto bookFilterDto,
                                        @RequestParam int page,
                                        @RequestParam int size) {
        return bookService.getAllByFilter(bookFilterDto, PageRequest.of(page, size));
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

    @GetMapping("/favBook/{id}")
    public FavoriteBook FavBookById(@PathVariable int id,
                                    @AuthenticationPrincipal CurrentUser currentUser) {
        return favoriteBookService.save(id, currentUser.getUser());
    }

    @GetMapping("/myFavBooks")
    public List<BookDto> findMyFavBooks(@AuthenticationPrincipal CurrentUser currentUser) {
        return favoriteBookService.findByUser(currentUser.getUser());
    }

    @DeleteMapping("/favBook/{id}")
    public void deleteFavBook(@PathVariable int id,
                              @AuthenticationPrincipal CurrentUser currentUser) {
        favoriteBookService.delete(currentUser.getUser(), id);
    }

    @GetMapping("/comment/{id}")
    public Comment comment(@PathVariable int id,
                           @RequestParam String comment,
                           @AuthenticationPrincipal CurrentUser currentUser) {
        return commentService.save(id, comment, currentUser.getUser());
    }

    @GetMapping("/viewComments/{id}")
    public List<Comment> viewComments(@PathVariable int id) {
        return commentService.findByBook(id);
    }
}
