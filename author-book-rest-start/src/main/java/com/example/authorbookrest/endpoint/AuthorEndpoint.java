package com.example.authorbookrest.endpoint;

import com.example.authorbookrest.dto.AuthorResponseDto;
import com.example.authorbookrest.dto.PagingResponseDto;
import com.example.authorbookrest.dto.SaveAuthorDto;
import com.example.authorbookrest.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/authors")
public class AuthorEndpoint {

    private final AuthorService authorService;

    @PostMapping
    @CrossOrigin(origins = "*")
    public AuthorResponseDto createAuthor(@RequestBody SaveAuthorDto authorDto) {
        return authorService.create(authorDto);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    public PagingResponseDto getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy,
            @RequestParam(value = "order", required = false, defaultValue = "DESC") String order) {
        var sort = Sort.by(Sort.Direction.fromString(order), orderBy);
        var pageable = PageRequest.of(page, size, sort);
        return authorService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getById(@PathVariable("id") int id) {
        var author = authorService.getById(id);
        return ResponseEntity.ok(author);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> update(@PathVariable("id") int id,
                                                    @RequestBody SaveAuthorDto authorDto) {
        return ResponseEntity.ok(authorService.update(id, authorDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> deleteById(@PathVariable("id") int id) {
        var byId = authorService.getById(id);
        authorService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
