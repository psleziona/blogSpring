package com.example.blog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;
    private String login;
    private String firstname;
    private String lastname;
    @Email
    private String email;
    private String password;
    @CreationTimestamp
    private LocalDateTime creationTime;

    @OneToMany(mappedBy = "article")
    private List<Article> articles;

    @OneToMany(mappedBy = "comment")
    private List<Comment> comments;

}
