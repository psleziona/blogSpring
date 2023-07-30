package com.example.blog.service;

import com.example.blog.auth.AuthService;
import com.example.blog.model.Article;
import com.example.blog.model.Comment;
import com.example.blog.model.Rate;
import com.example.blog.model.User;
import com.example.blog.repository.RateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RateServiceImpl implements RateService {
    private final ArticleService articleService;
    private final CommentService commentService;
    private final RateRepository rateRepository;
    private final AuthService authService;

    @Override
    public Rate getUserArticleRate(Integer idArticle) {
        User user = authService.getSessionUser();
    }

    @Override
    public void setArticleRate(Rate rate, Integer idArticle) {
        User author = authService.getSessionUser();
        rate.setAuthor(author);
        Article article = articleService.getArticle(idArticle).get();
        article.getArticleRates().add(rate);
        article.countRate();
        rateRepository.save(rate);
    }

    @Override
    public void setCommentRate(Rate rate, Integer idComment) {
        User author = authService.getSessionUser();
        rate.setAuthor(author);
        Comment comment = commentService.getComment(idComment).get();
        comment.getCommentRates().add(rate);
        comment.countRate();
        rateRepository.save(rate);
    }
}
