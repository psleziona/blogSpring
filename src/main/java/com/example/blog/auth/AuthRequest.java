package com.example.blog.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @Email(message = "Invalid email address format")
    private String email;

    @NotNull(message = "No password provided")
    @Size(min=8, max=32, message = "Password must be at least {min} characters long and not longer than {max}")
    private String password;
}
