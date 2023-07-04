package com.example.blog.service;

import com.example.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUser();
    Optional<User> getUserByEmail(String email);
    Page<User> getUsers(Pageable pageable);
    List<User> getUsers();
    Page<User> searchByName(String name, Pageable pageable);
    List<User> searchByName(String name);
    User setUser(User user);
    void deleteUser(Integer idUser);

}
