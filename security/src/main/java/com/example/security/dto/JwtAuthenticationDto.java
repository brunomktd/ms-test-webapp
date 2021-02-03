package com.example.security.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtAuthenticationDto {
    private String username;
    private String senha;
}
