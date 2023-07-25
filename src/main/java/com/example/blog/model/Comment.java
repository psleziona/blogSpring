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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComment;
    private String text;
    @CreationTimestamp
    private LocalDateTime creationTime;
    @LastModifiedDate
    private LocalDateTime modifyTime;
    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnoreProperties({"comments","articles","rates"})
    private User author;
    @ManyToOne
    @JoinColumn(name = "id_article")
    @JsonIgnoreProperties("article")
    private Article article;
    @ManyToMany
    @JoinTable(name = "comment_rate",
            joinColumns = {@JoinColumn(name = "id_comment")},
            inverseJoinColumns = {@JoinColumn(name = "id_rate")}
    )
    private List<Rate> commentRates;
    private Double averageRate;

    public Comment(Integer idComment, String text, LocalDateTime creationTime, User author, Article article) {
        this.idComment = idComment;
        this.text = text;
        this.creationTime = creationTime;
        this.author = author;
        this.article = article;
    }

    public void countRate() {
        averageRate = commentRates.stream().
                mapToDouble(Rate::getValue)
                .average().orElseGet(() -> 0.0);
    }
}
