package com.example.blog.service;

import com.example.blog.auth.AuthService;
import com.example.blog.model.Article;
import com.example.blog.model.User;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final AuthService authService;
    @Override
    public Optional<Article> getArticle(Integer idArticle) {
        return articleRepository.findById(idArticle);
    }

    @Override
    public Page<Article> getArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Page<Article> getUserArticles(Integer idUser, Pageable pageable) {
        return articleRepository.findArticlesFromAuthor(idUser, pageable);
    }

    @Override
    public Page<Article> searchByTitle(String title, Pageable pageable) {
        return articleRepository.findArticlesByTitleContainsIgnoreCase(title, pageable);
    }

    @Override
    public Page<Article> searchByAuthorName(String name, Pageable pageable) {
        return articleRepository.searchArticlesByAuthorName(name, pageable);
    }

    @Override
    public Page<Article> searchByRating(Integer rate, Pageable pageable) {
        return articleRepository.searchArticlesByArticleRatesIsGreaterThan(rate, pageable);
    }

    @Override
    public Page<Article> searchByPopularity(Pageable pageable) {
        return articleRepository.searchArticlesByPopularity(pageable);
    }

    @Override
    public Article setArticle(Article article) {
        User currentUser = authService.getSessionUser();
        article.setAuthor(currentUser);
        return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Integer idArticle) {
        articleRepository.deleteById(idArticle);
    }
}
