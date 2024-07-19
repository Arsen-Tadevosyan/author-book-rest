package com.example.authorbookrest.repository;


import com.example.authorbookrest.entity.Book;
import com.example.authorbookrest.entity.FavoriteBook;
import com.example.authorbookrest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteBookRepository extends JpaRepository<FavoriteBook, Integer> {

    FavoriteBook findByUserAndBook(User user, Book book);

    List<FavoriteBook> findByUser(User user);
}
