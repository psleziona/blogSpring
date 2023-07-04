package com.example.blog.repository;

import com.example.blog.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Page<Article> findArticlesByTitleContainsIgnoreCase(String title, Pageable pageable);
    @Query(value = "SELECT a FROM Article a WHERE a.author.idUser = :idUser")
    Page<Article> findArticlesFromAuthor(@Param("idUser") Integer idUser, Pageable pageable);
    @Query(value = "SELECT a FROM Article a WHERE a.author.lastname like %:lastname%")
    Page<Article> searchArticlesByAuthorName(@Param("lastname") String lastname, Pageable pageable);
    Page<Article> searchArticlesByArticleRatesIsGreaterThan(Integer rate, Pageable pageable);
    @Query(value = "SELECT a FROM Article a INNER JOIN a.articleRates ar order by ar.value")
    Page<Article> searchArticlesByPopularity(Pageable pageable);
    
}
