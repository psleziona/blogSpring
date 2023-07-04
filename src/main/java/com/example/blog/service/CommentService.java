package com.example.blog.service;

import com.example.blog.model.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> getComment(Integer idComment);
    Page<Comment> getArticleComments(Integer idArticle, Pageable pageable);
    Page<Comment> getUserComments(Integer idUser, Pageable pageable);
    Comment setComment(Comment comment, Integer idArticle);
    void deleteComment(Integer idComment);

}
