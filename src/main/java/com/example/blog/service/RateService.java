package com.example.blog.service;

import com.example.blog.model.Rate;

public interface RateService {
    Rate getUserArticleRate(Integer idArticle);
    void setArticleRate(Rate rate, Integer idArticle);
    void setCommentRate(Rate rate, Integer idComment);
}
