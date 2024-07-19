package com.example.authorbookrest.service;


import com.example.authorbookrest.entity.Comment;
import com.example.authorbookrest.entity.User;

import java.util.List;

public interface CommentService {

    Comment save(int id, String comment, User user);

    List<Comment> findByBook(int id);
}
