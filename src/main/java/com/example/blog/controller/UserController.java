package com.example.blog.controller;

import com.example.blog.model.User;
import com.example.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    @GetMapping("/users")
    Page<User> getUsers(Pageable pageable) {
        return userService.getUsers(pageable);
    }
    @GetMapping("/users/{idUser}")
    ResponseEntity<User> getUser(@PathVariable Integer idUser){
        return ResponseEntity.of(userService.getUser(idUser));
    }
    @GetMapping("/users/email/{email}")
    ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.of(userService.getUserByEmail(email));
    }
    @GetMapping("/users/name/{name}")
    Page<User> getUsersByName(@PathVariable String name, Pageable pageable) {
        return userService.searchByName(name, pageable);
    }
    @PostMapping("/users")
    ResponseEntity<Void> saveUser(@Valid @RequestBody User user) {
        User createdUser = userService.setUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idUser}").buildAndExpand(createdUser.getIdUser()).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/users/{idUser}")
    ResponseEntity<Void> updateUser(@PathVariable Integer idUser, @Valid @RequestBody User user) {
        return userService.getUser(idUser)
                .map(u -> {
                    userService.setUser(user);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/users/{idUser}")
    ResponseEntity<Void> deleteUser(@PathVariable Integer idUser) {
        return userService.getUser(idUser)
                .map(u -> {
                    userService.deleteUser(idUser);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
