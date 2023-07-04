package com.example.blog.repository;

import com.example.blog.model.Article;
import com.example.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
