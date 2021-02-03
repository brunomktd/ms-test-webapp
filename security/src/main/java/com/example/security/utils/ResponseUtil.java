package com.example.security.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseUtil<T> {

    private T data;
    private List<String> errors;

}
