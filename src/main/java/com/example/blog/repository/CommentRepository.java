package com.example.blog.repository;

import com.example.blog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Page<Comment> findCommentsByArticleIdArticle(Integer idArticle, Pageable pageable);
    Page<Comment> findCommentsByAuthorIdUser(Integer idUser, Pageable pageable);
}
