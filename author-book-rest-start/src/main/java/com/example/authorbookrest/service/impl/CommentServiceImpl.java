package com.example.authorbookrest.service.impl;

import com.example.authorbookrest.entity.Comment;
import com.example.authorbookrest.entity.User;
import com.example.authorbookrest.repository.CommentRepository;
import com.example.authorbookrest.service.BookService;
import com.example.authorbookrest.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookService bookService;

    @Override
    public Comment save(int id, String comment, User user) {
        log.info("Saving comment for book ID: {} by user: {}", id, user.getName());
        var byId = bookService.findById(id);
        Comment savedComment = commentRepository.save(Comment.builder()
                .book(byId)
                .content(comment)
                .user(user)
                .build());
        log.info("Comment saved successfully with ID: {}", savedComment.getId());
        return savedComment;
    }

    @Override
    public List<Comment> findByBook(int id) {
        var byId = bookService.findById(id);
        var comments = commentRepository.findByBook(byId);
        log.info("Fetched {} comments for book ID: {}", comments.size(), id);
        return comments;
    }
}
