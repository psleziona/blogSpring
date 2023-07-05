package com.example.blog.service;

import com.example.blog.model.Rate;

public interface RateService {
    void setArticleRate(Rate rate, Integer idArticle);
    void setCommentRate(Rate rate, Integer idComment);
}
