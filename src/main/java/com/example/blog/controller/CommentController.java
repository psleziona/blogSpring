package com.example.blog.controller;

import com.example.blog.model.Comment;
import com.example.blog.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comments/{idComment}")
    ResponseEntity<Comment> getComment(@PathVariable Integer idComment) {
        return ResponseEntity.of(commentService.getComment(idComment));
    }
    @GetMapping("/comments/article/{idArticle}")
    Page<Comment> getArticleComments(@PathVariable Integer idArticle, Pageable pageable) {
        return commentService.getArticleComments(idArticle, pageable);
    }
    @GetMapping("/comments/user/{idUser}")
    Page<Comment> getUserComments(@PathVariable Integer idUser, Pageable pageable) {
        return commentService.getUserComments(idUser, pageable);
    }
    @PostMapping("/comments")
    ResponseEntity<Void> saveComment(@Valid @RequestBody Comment comment) {
        Comment createdComment = commentService.setComment(comment);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idComment").buildAndExpand(comment.getIdComment()).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/comments/{idComment}")
    ResponseEntity<Void> updateComment(@PathVariable Integer idComment, @Valid @RequestBody Comment comment) {
        return commentService.getComment(idComment)
                .map(c -> {
                    commentService.setComment(comment);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/comments/{idComment}")
    ResponseEntity<Void> deleteComment(@PathVariable Integer idComment) {
        return commentService.getComment(idComment)
                .map(c -> {
                    commentService.deleteComment(idComment);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
