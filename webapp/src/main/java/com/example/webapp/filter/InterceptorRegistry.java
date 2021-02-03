package com.example.webapp.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorRegistry implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    @Autowired
    public InterceptorRegistry(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/login", "/");
    }
}
