package com.example.blog.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRate;
    private Integer value;
    @ManyToMany(mappedBy = "commentRates")
    List<Comment> comments;
    @ManyToMany(mappedBy = "articleRates")
    List<Article> articles;

}
