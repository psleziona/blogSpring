package com.example.blog.controller;

import com.example.blog.model.Rate;
import com.example.blog.service.RateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RateController {
    private final RateService rateService;
    @PostMapping("/rate/article/{idArticle}")
    ResponseEntity<Void> addArticleRate(@PathVariable Integer idArticle, @Valid @RequestBody Rate rate) {
        rateService.setArticleRate(rate, idArticle);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/rate/comment/{idComment}")
    ResponseEntity<Void> addCommentRate(@PathVariable Integer idComment, @Valid @RequestBody Rate rate) {
        rateService.setCommentRate(rate, idComment);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
