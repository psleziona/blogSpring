package com.example.blog.auth;


import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@Component
@SessionScope
public class TokenHolder {
    private String token;

    public void clear() {
        this.token = null;
    }
}
