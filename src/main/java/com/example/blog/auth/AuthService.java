package com.example.blog.auth;

import com.example.blog.config.JwtService;
import com.example.blog.model.CustomUserDetails;
import com.example.blog.model.Role;
import com.example.blog.model.User;
import com.example.blog.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.User);
        CustomUserDetails userDetails = new CustomUserDetails(userService.setUser(user));
        String token = jwtService.generateToken(userDetails);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse authenticate(@NotNull String email, @NotNull String password) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        var user = userService
                .getUserByEmail(email)
                .map(u -> new CustomUserDetails(u))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", email)));
        var token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        return authenticate(authRequest.getEmail(), authRequest.getPassword());
    }
}
