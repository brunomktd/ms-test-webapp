package com.example.webapp.vo;

public class LoginVO {
    private String username;
    private String senha;

    public String getUsername() {
        return username;
    }

    public LoginVO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public LoginVO setSenha(String senha) {
        this.senha = senha;
        return this;
    }
}
