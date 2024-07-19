package com.example.authorbookrest.repository;


import com.example.authorbookrest.entity.Book;
import com.example.authorbookrest.entity.FavoriteBook;
import com.example.authorbookrest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteBookRepository extends JpaRepository<FavoriteBook, Integer> {

    Optional<FavoriteBook> findByUserAndBook(User user, Book book);

    List<FavoriteBook> findByUser(User user);
}
