package com.example.blog.service;

import com.example.blog.model.Rate;

public interface RateService {
    Rate setArticleRate(Rate rate, Integer idArticle);
    Rate setCommentRate(Rate rate, Integer idComment);
}
