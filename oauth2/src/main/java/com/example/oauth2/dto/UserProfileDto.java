package com.example.oauth2.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserProfileDto {
    private String name;
    private String email;
}
