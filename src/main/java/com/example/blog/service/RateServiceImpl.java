package com.example.blog.service;

import com.example.blog.model.Article;
import com.example.blog.model.Comment;
import com.example.blog.model.Rate;
import com.example.blog.repository.RateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RateServiceImpl implements RateService {
    private final ArticleService articleService;
    private final CommentService commentService;
    private final RateRepository rateRepository;
    @Override
    public void setArticleRate(Rate rate, Integer idArticle) {
        Article article = articleService.getArticle(idArticle).get();
        article.getArticleRates().add(rate);
        rateRepository.save(rate);
    }

    @Override
    public void setCommentRate(Rate rate, Integer idComment) {
        Comment comment = commentService.getComment(idComment).get();
        comment.getCommentRates().add(rate);
        rateRepository.save(rate);
    }
}
