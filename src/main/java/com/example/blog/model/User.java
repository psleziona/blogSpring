package com.example.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    private Role role;
    @CreationTimestamp
    private LocalDateTime creationTime;
    private Boolean active;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties("author")
    private List<Article> articles;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties("author")
    private List<Comment> comments;

    public User(Integer idUser, String firstname, String lastname, String email, String password, LocalDateTime creationTime, Boolean active) {
        this.idUser = idUser;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.creationTime = creationTime;
        this.active = active;
        this.role = Role.Admin;
    }

    public User(Integer idUser, String firstname, String lastname, String email, String password, LocalDateTime creationTime,Role role, Boolean active) {
        this.idUser = idUser;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.creationTime = creationTime;
        this.active = active;
        this.role = role;
    }
}
