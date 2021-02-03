package com.example.oauth2.controller;

import com.example.oauth2.dto.UserProfileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class HelloController {
    @GetMapping("/")
    public String hello(){
        return "Hello security";
    }

    @GetMapping("/api/users/me")
    public ResponseEntity<UserProfileDto> profile(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Object credentials = authentication.getCredentials();
        Object details = authentication.getDetails();
        Object principal = authentication.getPrincipal();
        boolean authenticated = authentication.isAuthenticated();

        return ResponseEntity.ok(new UserProfileDto());
    }
}
