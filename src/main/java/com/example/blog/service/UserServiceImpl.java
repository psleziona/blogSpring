package com.example.blog.service;

import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public Optional<User> getUser(Integer idUser) {
        return userRepository.findById(idUser);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.searchUserByEmailEquals(email);
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> searchByName(String name, Pageable pageable) {
        return userRepository.searchUserByLastnameContainingIgnoreCase(name, pageable);
    }

    @Override
    public User setUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer idUser) {
        userRepository.deleteById(idUser);
    }
}
