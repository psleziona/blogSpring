package com.example.blog.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idArticle;
    private String text;
    @CreationTimestamp
    private LocalDateTime creationTime;
    @LastModifiedDate
    private LocalDateTime modifyTime;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User author;

}
