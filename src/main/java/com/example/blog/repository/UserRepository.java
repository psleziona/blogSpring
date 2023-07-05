package com.example.blog.repository;

import com.example.blog.model.Article;
import com.example.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> searchUserByEmailEquals(String email);
    Page<User> searchUserByLastnameContainingIgnoreCase(String name, Pageable pageable);
}
