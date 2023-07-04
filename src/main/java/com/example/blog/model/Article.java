package com.example.blog.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idArticle;
    private String title;
    private String text;
    @CreationTimestamp
    private LocalDateTime creationTime;
    @LastModifiedDate
    private LocalDateTime modifyTime;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User author;
    @OneToMany(mappedBy = "article")
    private List<Comment> comments;
    @ManyToMany
    @JoinTable(name="article_rate",
        joinColumns = {@JoinColumn(name = "id_article")},
        inverseJoinColumns = {@JoinColumn(name = "id_rate")})
    List<Rate> articleRates;

}
