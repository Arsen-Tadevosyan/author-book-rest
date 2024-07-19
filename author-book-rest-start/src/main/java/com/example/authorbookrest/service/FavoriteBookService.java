package com.example.authorbookrest.service;


import com.example.authorbookrest.dto.BookDto;
import com.example.authorbookrest.entity.FavoriteBook;
import com.example.authorbookrest.entity.User;

import java.util.List;

public interface FavoriteBookService {

    FavoriteBook save(int id, User user);


    List<BookDto> findByUser(User user);

    void delete(User user, int id);
}
