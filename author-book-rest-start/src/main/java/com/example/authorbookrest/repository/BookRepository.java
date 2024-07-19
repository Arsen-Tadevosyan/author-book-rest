package com.example.authorbookrest.repository;


import com.example.authorbookrest.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findByTitleContaining(String title, Pageable pageable);

    @Query(value = "SELECT * FROM book ORDER BY RAND() LIMIT 20", nativeQuery = true)
    List<Book> findRandomLimited();
}
