package com.example.authorbookrest.repository;

import com.example.authorbookrest.entity.Book;
import com.example.authorbookrest.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByBook(Book byId);
}
