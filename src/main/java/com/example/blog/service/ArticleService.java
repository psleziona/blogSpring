package com.example.blog.service;

import com.example.blog.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Optional<Article> getArticle(Integer idArticle);
    Page<Article> getArticles(Pageable pageable);
    Page<Article> searchByTitle(String title, Pageable pageable);
    List<Article> searchByTitle(String title);
    Page<Article> searchByAuthorName(String name, Pageable pageable);
    List<Article> searchByAuthorName(String name);
    Page<Article> searchByRating(Integer rate, Pageable pageable);
    List<Article> searchByRating(Integer rating);
    Page<Article> searchByPopularity(Integer limit, Pageable pageable);
    List<Article> searchByPopularity(Integer limit);
    Article saveArticle(Article article);
    void deleteArticle(Integer articleId);
}
