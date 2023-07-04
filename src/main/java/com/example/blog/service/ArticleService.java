package com.example.blog.service;

import com.example.blog.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Optional<Article> getArticle(Integer idArticle);
    Page<Article> getArticles(Pageable pageable);
    Page<Article> getUserArticles(Integer idUser, Pageable pageable);
    Page<Article> searchByTitle(String title, Pageable pageable);
    Page<Article> searchByAuthorName(String name, Pageable pageable);
    Page<Article> searchByRating(Integer rate, Pageable pageable);
    Page<Article> searchByPopularity(Integer limit, Pageable pageable);
    Article setArticle(Article article);
    void deleteArticle(Integer articleId);
}
