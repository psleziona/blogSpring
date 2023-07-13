package com.example.blog.controller;

import com.example.blog.model.Article;
import com.example.blog.service.ArticleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ArticleController {
    private final ArticleService articleService;
    @GetMapping("/articles")
    Page<Article> getArticles(Pageable pageable) {
        return articleService.getArticles(pageable);
    }
    @GetMapping("/articles/{idArticle}")
    ResponseEntity<Article> getArticle(@PathVariable Integer idArticle) {
       return ResponseEntity.of(articleService.getArticle(idArticle));
    }
    @GetMapping("/articles/user/{idUser}")
    Page<Article> getUserArticles(@PathVariable Integer idUser, Pageable pageable) {
        return articleService.getUserArticles(idUser, pageable);
    }
    @GetMapping("/articles/title/{title}")
    Page<Article> getArticlesByTitle(@PathVariable String title, Pageable pageable) {
        return articleService.searchByTitle(title, pageable);
    }
    @GetMapping("/articles/author/{author}")
    Page<Article> getArticlesByAuthor(@PathVariable String author, Pageable pageable) {
        return articleService.searchByAuthorName(author, pageable);
    }
    @GetMapping("/articles/rate/{rate}")
    Page<Article> getArticlesByRating(@PathVariable Integer rate, Pageable pageable) {
        return articleService.searchByRating(rate, pageable);
    }
    @GetMapping("/articles/popular")
    Page<Article> getArticlesByPopularity(Pageable pageable) {
        return articleService.searchByPopularity(pageable);
    }
    @PostMapping("/articles")
    ResponseEntity<Void> saveArticle(@Valid @RequestBody Article article) {
        Article createdArticle = articleService.setArticle(article);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idArticle}").buildAndExpand(createdArticle.getIdArticle()).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/articles/{idArticle}")
    ResponseEntity<Void> updateArticle(@Valid @RequestBody Article article, @PathVariable Integer idArticle) {
        return articleService.getArticle(idArticle)
                .map(a -> {
                    articleService.setArticle(article);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/articles/{idArticle}")
    ResponseEntity<Void> deleteArticle(@PathVariable Integer idArticle) {
        return articleService.getArticle(idArticle)
                .map(a -> {
                    articleService.deleteArticle(idArticle);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
