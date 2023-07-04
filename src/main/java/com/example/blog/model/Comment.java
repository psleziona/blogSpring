package com.example.blog.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
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
    private User author;
    @ManyToMany
    @JoinTable(name = "comment_rate",
            joinColumns = {@JoinColumn(name = "id_comment")},
            inverseJoinColumns = {@JoinColumn(name = "id_rate")}
    )
    List<Rate> commentRates;

}
