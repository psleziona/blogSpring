package com.example.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRate;
    private Integer value;
    @ManyToOne
    @JoinColumn(name= "id_user")
    @JsonIgnoreProperties("rates")
    private User author;
    @CreationTimestamp
    private LocalDateTime createdTime;
    @ManyToMany(mappedBy = "commentRates")
    @JsonIgnoreProperties("commentRates")
    List<Comment> comments;
    @ManyToMany(mappedBy = "articleRates")
    @JsonIgnoreProperties("articleRates")
    List<Article> articles;

    public Rate(Integer idRate, Integer value, LocalDateTime createdTime) {
        this.idRate = idRate;
        this.value = value;
        this.createdTime = createdTime;
    }
}
