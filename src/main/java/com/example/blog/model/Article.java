package com.example.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
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
    @JsonIgnoreProperties("articles")
    private User author;
    @OneToMany(mappedBy = "article")
    @JsonIgnoreProperties("author")
    private List<Comment> comments;
    @ManyToMany
    @JoinTable(name="article_rate",
        joinColumns = {@JoinColumn(name = "id_article")},
        inverseJoinColumns = {@JoinColumn(name = "id_rate")})
    List<Rate> articleRates;

    public Article(Integer idArticle, String title, String text, LocalDateTime creationTime, User author) {
        this.idArticle = idArticle;
        this.title = title;
        this.text = text;
        this.creationTime = creationTime;
        this.author = author;
    }
}
