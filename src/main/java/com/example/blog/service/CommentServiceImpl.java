package com.example.blog.service;

import com.example.blog.auth.AuthService;
import com.example.blog.model.Article;
import com.example.blog.model.Comment;
import com.example.blog.model.User;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final AuthService authService;
    @Override
    public Optional<Comment> getComment(Integer idComment) {
        return commentRepository.findById(idComment);
    }

    @Override
    public Page<Comment> getArticleComments(Integer idArticle, Pageable pageable) {
        return commentRepository.findCommentsByArticleIdArticle(idArticle, pageable);
    }

    @Override
    public Page<Comment> getUserComments(Integer idUser, Pageable pageable) {
        return commentRepository.findCommentsByAuthorIdUser(idUser, pageable);
    }

    @Override
    public Comment setComment(Comment comment) {
        User author = authService.getSessionUser();
        comment.setAuthor(author);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Integer idComment) {
        commentRepository.deleteById(idComment);
    }
}
